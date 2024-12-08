package org.spring.datingsite.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.datingsite.service.UserService;
import org.spring.datingsite.web.dto.ExceptionResponse;
import org.spring.datingsite.web.dto.auth.SignInDto;
import org.spring.datingsite.web.dto.auth.SignUpDto;
import org.spring.datingsite.web.dto.auth.TokenDto;
import org.spring.datingsite.web.dto.user.UserDto;
import org.spring.datingsite.web.mapper.AuthMapper;
import org.spring.datingsite.web.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Authentication controller")
@RestController()
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final AuthMapper authMapper;

    private final UserMapper userMapper;

    @Operation(summary = "Sign up", description = "Create a new user via registration.", responses = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TokenDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User with such attribute already exists.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )
            ),
    })
    @PostMapping("/sign-up")
    public ResponseEntity<TokenDto> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        var token = userService.signUp(authMapper.toUserEntity(signUpDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(authMapper.toTokenDto(token));
    }

    @Operation(summary = "Sign in", description = "Sign in to the system.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TokenDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User with such email not found.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Invalid password.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )
            ),
    })
    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> signIn(@Valid @RequestBody SignInDto signInDto) {
        var token = userService.signIn(signInDto.getEmail(), signInDto.getPassword());
        return ResponseEntity.ok(authMapper.toTokenDto(token));
    }

    @Operation(
            summary = "Get current user",
            description = "Get information about the current user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "403", content = @Content)
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@RequestAttribute("userId") String userId) {
        var user = userService.getById(UUID.fromString(userId));
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}

package org.spring.datingsite.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.spring.datingsite.service.UserService;
import org.spring.datingsite.web.dto.ExceptionResponse;
import org.spring.datingsite.web.dto.PageWithSort;
import org.spring.datingsite.web.dto.query.UserQueries;
import org.spring.datingsite.web.dto.user.UserDto;
import org.spring.datingsite.web.dto.user.UserUpdateDto;
import org.spring.datingsite.web.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "User controller")
@RestController()
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Operation(
            summary = "Get all users",
            description = "Get all users with pagination and sorting."
    )
    @GetMapping()
    public ResponseEntity<Page<UserDto>> getUsers(UserQueries queries, PageWithSort pageable) {
        return ResponseEntity.ok(userMapper.toDto(userService.getAll(queries, pageable)));
    }

    @Operation(
            summary = "Get user",
            description = "Get user by id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with such id not found.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        var user = this.userService.getById(UUID.fromString(id));
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @Operation(
            summary = "Update user",
            description = "Update user by id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with such id not found.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserUpdateDto userUpdateDto) {
        var user = this.userService.getById(UUID.fromString(id));
        var userToUpdate = userMapper.partialUpdate(userUpdateDto, user);
        return ResponseEntity.ok(userMapper.toDto(userService.update(userToUpdate)));
    }

    @Operation(
            summary = "Delete user",
            description = "Delete user by id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with such id not found.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable String id) {
        var user = this.userService.delete(UUID.fromString(id));
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}

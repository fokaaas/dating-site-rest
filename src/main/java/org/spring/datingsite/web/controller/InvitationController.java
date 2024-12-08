package org.spring.datingsite.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.spring.datingsite.service.InvitationService;
import org.spring.datingsite.web.dto.ExceptionResponse;
import org.spring.datingsite.web.dto.PageWithSort;
import org.spring.datingsite.web.dto.invitation.InvitationCreateOrUpdateDto;
import org.spring.datingsite.web.dto.invitation.InvitationDto;
import org.spring.datingsite.web.dto.invitation.InvitationIdDto;
import org.spring.datingsite.web.dto.query.InvitationQueries;
import org.spring.datingsite.web.mapper.InvitationMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Invitation controller")
@SecurityRequirement(name = "bearerAuth")
@RestController()
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    private final InvitationMapper invitationMapper;

    @Operation(
            summary = "Get all invitations",
            description = "Get all invitations with pagination and sorting."
    )
    @GetMapping()
    public ResponseEntity<Page<InvitationDto>> getInvitations(InvitationQueries queries, PageWithSort pageWithSort) {
        return ResponseEntity.ok(invitationMapper.toDto(this.invitationService.getAll(queries, pageWithSort)));
    }

    @Operation(
            summary = "Get invitation",
            description = "Get invitation by sender and receiver id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = InvitationDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Invitation with such receiver and sender id not found.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/{senderId}/users/{receiverId}")
    public ResponseEntity<InvitationDto> getInvitation(
            @PathVariable String senderId,
            @PathVariable String receiverId
    ) {
        var id = invitationMapper.toIdEntity(UUID.fromString(senderId), UUID.fromString(receiverId));
        return ResponseEntity.ok(invitationMapper.toDto(this.invitationService.getById(id)));
    }

    @Operation(
            summary = "Create invitation",
            description = "Create a new invitation.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = InvitationIdDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Invitation already exists.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/{senderId}/users/{receiverId}")
    public ResponseEntity<InvitationIdDto> createInvitation(
            @PathVariable String senderId,
            @PathVariable String receiverId,
            @RequestBody InvitationCreateOrUpdateDto invitationCreateOrUpdateDto
    ) {
        var invitation = invitationMapper.toEntity(
                UUID.fromString(senderId), UUID.fromString(receiverId),
                invitationCreateOrUpdateDto
        );
        var invitationIdDto = invitationMapper.toIdDto(invitationService.create(invitation));
        return ResponseEntity.status(HttpStatus.CREATED).body(invitationIdDto);
    }

    @Operation(
            summary = "Update invitation",
            description = "Update invitation by sender and receiver id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = InvitationDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Invitation with such receiver and sender id not found.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    )
            }
    )
    @PatchMapping("/{senderId}/users/{receiverId}")
    public ResponseEntity<InvitationDto> updateInvitation(
            @PathVariable String senderId,
            @PathVariable String receiverId,
            @RequestBody InvitationCreateOrUpdateDto invitationCreateOrUpdateDto
    ) {
        var invitation = invitationService.getById(
                invitationMapper.toIdEntity(UUID.fromString(senderId), UUID.fromString(receiverId))
        );
        var invitationToUpdate = invitationMapper.partialUpdate(invitationCreateOrUpdateDto, invitation);
        return ResponseEntity.ok(invitationMapper.toDto(invitationService.update(invitationToUpdate)));
    }

    @Operation(
            summary = "Delete invitation",
            description = "Delete invitation by sender and receiver id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = InvitationDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Invitation with such receiver and sender id not found.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{senderId}/users/{receiverId}")
    public ResponseEntity<InvitationDto> deleteInvitation(
            @PathVariable String senderId,
            @PathVariable String receiverId
    ) {
        var id = invitationMapper.toIdEntity(UUID.fromString(senderId), UUID.fromString(receiverId));
        return ResponseEntity.ok(invitationMapper.toDto(this.invitationService.delete(id)));
    }
}

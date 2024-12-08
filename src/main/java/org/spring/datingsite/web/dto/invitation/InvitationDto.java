package org.spring.datingsite.web.dto.invitation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class InvitationDto {

    @Schema(description = "Id of the invitation receiver")
    private UUID receiverId;

    @Schema(description = "Id of the invitation sender")
    private UUID senderId;

    @Schema(description = "If user accepted invitation")
    private Boolean isAccepted;
}

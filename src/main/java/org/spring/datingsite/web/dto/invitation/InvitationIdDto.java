package org.spring.datingsite.web.dto.invitation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class InvitationIdDto {

    @Schema(description = "Id of the invitation sender")
    private UUID senderId;

    @Schema(description = "Id of the invitation receiver")
    private UUID receiverId;
}

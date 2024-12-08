package org.spring.datingsite.web.dto.invitation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InvitationCreateOrUpdateDto {

    @Schema(description = "If user accepted invitation")
    private Boolean isAccepted;
}

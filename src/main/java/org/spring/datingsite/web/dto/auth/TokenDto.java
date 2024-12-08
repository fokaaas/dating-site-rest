package org.spring.datingsite.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class TokenDto implements Serializable {
    @Schema(description = "Jwt token")
    private String token;
}

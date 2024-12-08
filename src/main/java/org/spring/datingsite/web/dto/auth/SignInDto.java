package org.spring.datingsite.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInDto {
    @Schema(description = "Email of the user", example = "stas@gmail.com")
    @NotBlank(message = "Email is required.")
    private String email;

    @Schema(description = "Password of the user")
    @NotBlank(message = "Password is required.")
    private String password;
}

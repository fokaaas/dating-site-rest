package org.spring.datingsite.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.spring.datingsite.domain.Gender;

import java.time.LocalDate;

@Data
public class SignUpDto {
    @Schema(description = "First name of the user", example = "John")
    @NotBlank(message = "First name is required")
    @Pattern(
            regexp = "(^[А-ЩЬЮЯЇІЄҐ][а-щьюяїієґ]*(['-][А-ЩЬЮЯЇІЄҐ][а-щьюяїієґ]*)*$)|(^([A-Z][a-z]*(['-][A-Z][a-z]*)*)$)",
            message = "First name must contain only allowed letters"
    )
    @Size(min = 2, max = 32, message = "First name must be between 2 and 32 characters")
    private String firstName;

    @Schema(description = "Middle name of the user", example = "Doe")
    @Pattern(
            regexp = "(^[А-ЩЬЮЯЇІЄҐ][а-щьюяїієґ]*(['-][А-ЩЬЮЯЇІЄҐ][а-щьюяїієґ]*)*$)|(^([A-Z][a-z]*(['-][A-Z][a-z]*)*)$)",
            message = "Middle name must contain only allowed letters"
    )
    @Size(min = 2, max = 32, message = "Middle name must be between 2 and 32 characters")
    private String middleName;

    @Schema(description = "Last name of the user", example = "Smith")
    @NotBlank(message = "Last name is required")
    @Pattern(
            regexp = "(^[А-ЩЬЮЯЇІЄҐ][а-щьюяїієґ]*(['-][А-ЩЬЮЯЇІЄҐ][а-щьюяїієґ]*)*$)|(^([A-Z][a-z]*(['-][A-Z][a-z]*)*)$)",
            message = "Last name must contain only allowed letters"
    )
    @Size(min = 2, max = 32, message = "Last name must be between 2 and 32 characters")
    private String lastName;

    @Schema(description = "Gender of the user", example = "MALE")
    @NotNull(message = "Gender is required")
    private Gender gender;

    @Schema(description = "Photo link of the user", example = "https://example.com/photo.jpg")
    @URL(message = "Photo link must be a valid URL")
    private String photoLink;

    @Schema(description = "Email of the user", example = "stas@gmail.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Schema(description = "Password of the user")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    private String password;

    @Schema(description = "Phone number of the user", example = "+380123456789")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+\\d{1,15}$", message = "Phone number must be valid")
    private String phoneNumber;

    @Schema(description = "Telegram link of the user", example = "https://t.me/john_doe")
    @URL(message = "Telegram link must be a valid URL")
    private String telegramLink;

    @Schema(description = "Birth date of the user", example = "2000-01-01")
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Schema(description = "Settlement of the user", example = "Kyiv")
    @NotBlank(message = "Settlement is required")
    @Size(min = 2, max = 64, message = "Settlement must be between 2 and 64 characters")
    private String settlement;

    @Schema(description = "About me of the user", example = "I am a software engineer")
    @Size(max = 1024, message = "About me must be less than 1024 characters")
    private String aboutMe;
}

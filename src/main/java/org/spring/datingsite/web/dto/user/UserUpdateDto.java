package org.spring.datingsite.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.spring.datingsite.domain.Gender;

import java.time.LocalDate;

@Data
public class UserUpdateDto {

    @Schema(description = "User's first name", example = "John")
    private String firstName;

    @Schema(description = "User's middle name", example = "Doe")
    private String middleName;

    @Schema(description = "User's last name", example = "Smith")
    private String lastName;

    @Schema(description = "Gender of the user", example = "OTHER")
    private Gender gender;

    @Schema(description = "Photo link of the user", example = "https://example/photo.jpg")
    private String photoLink;

    @Schema(description = "Email of the user", example = "stas@gmail.com")
    private String email;

    @Schema(description = "Phone number of the user", example = "+380123456789")
    private String phoneNumber;

    @Schema(description = "Telegram link of the user", example = "https://t.me/username")
    private String telegramLink;

    @Schema(description = "Birth date of the user", example = "2000-01-01")
    private LocalDate birthDate;

    @Schema(description = "Settlement of the user", example = "Kyiv")
    private String settlement;

    @Schema(description = "About me of the user", example = "I am a student")
    private String aboutMe;
}

package org.spring.datingsite.web.dto.query;

import lombok.Data;
import org.spring.datingsite.domain.Gender;

import java.time.Year;
import java.util.UUID;

@Data
public class UserQueries {
    private UUID id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Gender gender;

    private Year birthYearFrom;

    private Year birthYearTo;

    private String settlement;

    private String aboutMe;
}

package org.spring.datingsite.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.spring.datingsite.domain.User;
import org.spring.datingsite.web.dto.auth.SignUpDto;
import org.spring.datingsite.web.dto.auth.TokenDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {

    User toUserEntity(SignUpDto signUpDto);

    TokenDto toTokenDto(String token);
}

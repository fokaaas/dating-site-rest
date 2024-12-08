package org.spring.datingsite.web.mapper;

import org.mapstruct.*;
import org.spring.datingsite.domain.User;
import org.spring.datingsite.web.dto.user.UserDto;
import org.spring.datingsite.web.dto.user.UserUpdateDto;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto toDto(User user);

    default Page<UserDto> toDto(Page<User> users) {
        return users.map(this::toDto);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserUpdateDto userUpdateDto, @MappingTarget User user);
}

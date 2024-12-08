package org.spring.datingsite.web.mapper;

import org.mapstruct.*;
import org.spring.datingsite.domain.Invitation;
import org.spring.datingsite.domain.InvitationId;
import org.spring.datingsite.web.dto.invitation.InvitationCreateOrUpdateDto;
import org.spring.datingsite.web.dto.invitation.InvitationDto;
import org.spring.datingsite.web.dto.invitation.InvitationIdDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvitationMapper {

    @Mapping(target = "senderId", source = "id.senderId")
    @Mapping(target = "receiverId", source = "id.receiverId")
    InvitationDto toDto(Invitation invitation);
    
    default Page<InvitationDto> toDto(Page<Invitation> invitation) {
        return invitation.map(this::toDto);
    }


    @Mapping(target = "id.senderId", source = "senderId")
    @Mapping(target = "id.receiverId", source = "receiverId")
    Invitation toEntity(
            UUID senderId,
            UUID receiverId,
            InvitationCreateOrUpdateDto invitationCreateOrUpdateDto
    );

    InvitationIdDto toIdDto(InvitationId invitationId);

    InvitationId toIdEntity(UUID senderId, UUID receiverId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Invitation partialUpdate(
            InvitationCreateOrUpdateDto invitationCreateOrUpdateDto,
            @MappingTarget Invitation invitation
    );
}

package org.spring.datingsite.service;

import org.spring.datingsite.domain.Invitation;
import org.spring.datingsite.domain.InvitationId;
import org.spring.datingsite.web.dto.PageWithSort;
import org.spring.datingsite.web.dto.query.InvitationQueries;
import org.springframework.data.domain.Page;

public interface InvitationService {

    Page<Invitation> getAll(InvitationQueries queries, PageWithSort pageWithSort);

    Invitation getById(InvitationId id);

    InvitationId create(Invitation invitation);

    Invitation update(Invitation invitation);

    Invitation delete(InvitationId id);
}

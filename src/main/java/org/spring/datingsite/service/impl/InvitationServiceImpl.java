package org.spring.datingsite.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.spring.datingsite.data.InvitationRepository;
import org.spring.datingsite.data.UserRepository;
import org.spring.datingsite.domain.Invitation;
import org.spring.datingsite.domain.InvitationId;
import org.spring.datingsite.domain.specification.InvitationSpecification;
import org.spring.datingsite.exception.EntityAlreadyExistsException;
import org.spring.datingsite.exception.EntityNotFoundException;
import org.spring.datingsite.service.InvitationService;
import org.spring.datingsite.service.UserService;
import org.spring.datingsite.web.dto.PageWithSort;
import org.spring.datingsite.web.dto.query.InvitationQueries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    @Override
    public Page<Invitation> getAll(InvitationQueries queries, PageWithSort pageWithSort) {
        var specification = InvitationSpecification.filter(queries);

        String sortField = Optional.ofNullable(pageWithSort)
                .map(PageWithSort::getSortBy)
                .orElse("createdAt");

        Sort sort = Optional.ofNullable(pageWithSort)
                .map(PageWithSort::getSort)
                .filter("desc"::equalsIgnoreCase)
                .map(direction -> Sort.by(sortField).descending())
                .orElse(Sort.by(sortField).ascending());

        int page = Optional.ofNullable(pageWithSort)
                .map(PageWithSort::getPage)
                .orElse(0);

        int size = Optional.ofNullable(pageWithSort)
                .map(PageWithSort::getSize)
                .orElse(10);

        Pageable pageable = PageRequest.of(page, size, sort);
        return invitationRepository.findAll(specification, pageable);
    }

    @Override
    public Invitation getById(InvitationId id) {
        return invitationRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invitation", "receiver and sender id"));
    }

    @Override
    public InvitationId create(Invitation invitation) {
        var id = invitation.getId();
        var sender = userService.getById(id.getSenderId());
        var receiver = userService.getById(id.getReceiverId());

        if (invitationRepository.existsById(id)) {
            throw new EntityAlreadyExistsException("Invitation");
        }
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitationRepository.save(invitation);
        return invitation.getId();
    }

    @Override
    public Invitation update(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    @Override
    @Transactional
    public Invitation delete(InvitationId id) {
        var invitation = getById(id);
        invitation.removeSender(invitation.getSender());
        invitation.removeReceiver(invitation.getReceiver());
        userRepository.save(invitation.getSender());
        userRepository.save(invitation.getReceiver());
        invitationRepository.delete(invitation);
        return invitation;
    }
}

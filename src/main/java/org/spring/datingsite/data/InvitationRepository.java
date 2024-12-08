package org.spring.datingsite.data;

import org.spring.datingsite.domain.Invitation;
import org.spring.datingsite.domain.InvitationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, InvitationId>, JpaSpecificationExecutor<Invitation> {

    Page<Invitation> findAll(Specification<Invitation> specification, Pageable pageable);
}

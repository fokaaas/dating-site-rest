package org.spring.datingsite.domain.specification;

import org.spring.datingsite.domain.Invitation;
import org.spring.datingsite.web.dto.query.InvitationQueries;
import org.springframework.data.jpa.domain.Specification;

public class InvitationSpecification extends BaseSpecification {

    public static Specification<Invitation> filter(InvitationQueries invitationQueries) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                createEqualityPredicate(criteriaBuilder, root.get("id").get("receiverId"), invitationQueries.getReceiverId()),
                createEqualityPredicate(criteriaBuilder, root.get("id").get("senderId"), invitationQueries.getSenderId()),
                createEqualityPredicate(criteriaBuilder, root.get("isAccepted"), invitationQueries.getIsAccepted())
        );
    }
}

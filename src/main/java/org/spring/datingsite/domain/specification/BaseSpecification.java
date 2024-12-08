package org.spring.datingsite.domain.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class BaseSpecification {

    protected static Predicate createEqualityPredicate(CriteriaBuilder criteriaBuilder, Path<?> path, Object value) {
        return value == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(path, value);
    }
}

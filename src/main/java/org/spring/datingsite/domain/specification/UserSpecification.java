package org.spring.datingsite.domain.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.spring.datingsite.domain.User;
import org.spring.datingsite.web.dto.query.UserQueries;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.Year;

public class UserSpecification extends BaseSpecification {

    public static Specification<User> filter(UserQueries userQueries) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                createLikePredicate(criteriaBuilder, root.get("firstName"), userQueries.getFirstName()),
                createLikePredicate(criteriaBuilder, root.get("lastName"), userQueries.getLastName()),
                createLikePredicate(criteriaBuilder, root.get("middleName"), userQueries.getMiddleName()),
                createEqualityPredicate(criteriaBuilder, root.get("gender"), userQueries.getGender()),
                createYearPredicate(criteriaBuilder, root.get("birthDate"), userQueries.getBirthYearFrom(), true),
                createYearPredicate(criteriaBuilder, root.get("birthDate"), userQueries.getBirthYearTo(), false),
                createLikePredicate(criteriaBuilder, root.get("settlement"), userQueries.getSettlement()),
                createLikePredicate(criteriaBuilder, root.get("aboutMe"), userQueries.getAboutMe())
        );
    }

    private static Predicate createLikePredicate(CriteriaBuilder criteriaBuilder, Path<String> path, String value) {
        if (value == null) return criteriaBuilder.conjunction();
        String lowerValue = "%" + value.toLowerCase() + "%";
        Expression<String> lowerPath = criteriaBuilder.lower(path);
        return criteriaBuilder.like(lowerPath, lowerValue);
    }

    private static Predicate createYearPredicate(CriteriaBuilder criteriaBuilder, Path<LocalDate> path, Year year, boolean isFrom) {
        if (year == null) return criteriaBuilder.conjunction();
        return isFrom ?
                criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.function("year", Integer.class, path), year.getValue()) :
                criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.function("year", Integer.class, path), year.getValue());
    }
}

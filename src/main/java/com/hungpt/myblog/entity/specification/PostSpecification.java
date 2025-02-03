package com.hungpt.myblog.entity.specification;

import com.hungpt.myblog.entity.Post;
import com.hungpt.myblog.entity.specification.criteria.PostCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@RequiredArgsConstructor
public final class PostSpecification implements Specification<Post> {
    private final PostCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        // Title filter
        if (criteria.getTitle() != null && !criteria.getTitle().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + criteria.getTitle().toLowerCase() + "%"));
        }

        // Status filter
        if (criteria.getStatus() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
        }

        // Tag filter
        if (criteria.getTagId() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.isMember(criteria.getTagId(), root.get("tags")));
        }

        // Category filter
        if (criteria.getCategoryId() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category").get("id"), criteria.getCategoryId()));
        }

        // Date range filter (start date)
        if (criteria.getStartDate() != null && !criteria.getStartDate().isEmpty()) {
            LocalDate startDate = LocalDate.parse(criteria.getStartDate());
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate));
        }

        // Date range filter (end date)
        if (criteria.getEndDate() != null && !criteria.getEndDate().isEmpty()) {
            LocalDate endDate = LocalDate.parse(criteria.getEndDate());
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate));
        }

        return predicate;
    }
}

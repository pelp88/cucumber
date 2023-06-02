package ru.coderiders.cucumber.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.coderiders.cucumber.entity.User;

public class UserSpecification {

    public static Specification<User> roleLike(String role) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("role"), "%" + role + "%"));
    }

    public static Specification<User> idBelow(Long number) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("id"), number));
    }

    public static Specification<User> idAbove(Long number) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"), number));
    }
}

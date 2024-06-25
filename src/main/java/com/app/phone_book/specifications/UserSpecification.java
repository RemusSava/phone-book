package com.app.phone_book.specifications;
import com.app.phone_book.models.User;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filterByCriteria(String email, String roleName) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
            }
            if (roleName != null && !roleName.isEmpty()) {
                var roleJoin = root.join("role", JoinType.INNER);
                predicates.add(criteriaBuilder.like(roleJoin.get("name"), "%" + roleName.toUpperCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

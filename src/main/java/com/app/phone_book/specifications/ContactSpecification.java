package com.app.phone_book.specifications;
import com.app.phone_book.models.Contact;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class ContactSpecification {

    public static Specification<Contact> filterByCriteria(String name, String email, String phoneNumber, String address, String job) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                Predicate firstNamePredicate = criteriaBuilder.like(root.get("firstName"), "%" + name + "%");
                Predicate lastNamePredicate = criteriaBuilder.like(root.get("lastName"), "%" + name + "%");
                predicates.add(criteriaBuilder.or(firstNamePredicate, lastNamePredicate));
            }
            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + phoneNumber + "%"));
            }
            if (address != null && !address.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }
            if (job != null && !job.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("jobTitle"), "%" + job + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

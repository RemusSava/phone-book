package com.app.phone_book.repositories;

import com.app.phone_book.models.Contact;
import com.app.phone_book.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findById(UUID id);

    User findByEmail(String email);

    void deleteById(UUID id);
}

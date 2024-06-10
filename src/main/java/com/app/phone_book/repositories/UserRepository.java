package com.app.phone_book.repositories;

import com.app.phone_book.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findById(UUID id);

    void deleteById(UUID id);
}

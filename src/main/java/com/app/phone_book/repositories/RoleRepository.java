package com.app.phone_book.repositories;

import com.app.phone_book.models.Group;
import com.app.phone_book.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(UUID id);

    void deleteById(UUID id);

    Role save(Role role);

    Role findByName(String name);
}

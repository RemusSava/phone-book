package com.app.phone_book.repositories;

import com.app.phone_book.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Group findById(UUID id);

    Group findByName(String name);

    void deleteById(UUID id);

    Group save(Group contact);
}

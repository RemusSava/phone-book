package com.app.phone_book.repositories;

import com.app.phone_book.models.Group;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends PagingAndSortingRepository<Group, Long> {
    Group findById(UUID id);

    void deleteById(Long id);

    Group save(Group contact);
}

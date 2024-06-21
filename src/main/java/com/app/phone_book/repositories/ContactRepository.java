package com.app.phone_book.repositories;

import com.app.phone_book.models.Contact;
import com.app.phone_book.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findById(UUID id);

    Page<Contact> findByUser(User user, Pageable pageable);

    void deleteById(UUID id);

    Contact save(Contact contact);

    long countByUser(User user);
}

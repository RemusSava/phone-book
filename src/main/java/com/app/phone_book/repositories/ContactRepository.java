package com.app.phone_book.repositories;

import com.app.phone_book.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findById(UUID id);
}

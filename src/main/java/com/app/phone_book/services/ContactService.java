package com.app.phone_book.services;

import com.app.phone_book.models.Contact;
import com.app.phone_book.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact getContactById(UUID id) {
        return contactRepository.findById(id);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}

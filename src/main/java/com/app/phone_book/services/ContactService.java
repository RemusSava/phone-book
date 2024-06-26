package com.app.phone_book.services;

import com.app.phone_book.models.Contact;
import com.app.phone_book.models.Group;
import com.app.phone_book.models.User;
import com.app.phone_book.repositories.ContactRepository;
import com.app.phone_book.repositories.GroupRepository;
import com.app.phone_book.repositories.UserRepository;
import com.app.phone_book.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public Page<Contact> getAllContacts(int page, int size, String token) {
        String userEmail = jwtUtil.extractUsername(token);
        User authenticatedUser = userRepository.findByEmail(userEmail);
        if (authenticatedUser != null) {
            return contactRepository.findByUser(authenticatedUser, PageRequest.of(page, size));
        }
        throw new RuntimeException("User not authenticated");
    }

    public Contact saveContact(Contact contact, String token, UUID groupId) {

        Group group = groupRepository.findById(groupId);
        contact.setGroup(group);

        if(token != null){
            String userEmail = jwtUtil.extractUsername(token);
            User authenticatedUser = userRepository.findByEmail(userEmail);

            if (authenticatedUser != null) {
                contact.setUser(authenticatedUser);
                return contactRepository.save(contact);
            }
        }

        Contact existingContact = contactRepository.findById(contact.getId());
        contact.setUser(existingContact.getUser());

        return contactRepository.save(contact);

    }

    public Contact getContactById(UUID id) {
        return contactRepository.findById(id);
    }

    @Transactional
    public void deleteContact(UUID id) {
        contactRepository.deleteById(id);
    }

    public long count(String token) {
        String userEmail = jwtUtil.extractUsername(token);
        User authenticatedUser = userRepository.findByEmail(userEmail);
        return contactRepository.countByUser(authenticatedUser);
    }
}

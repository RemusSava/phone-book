package com.app.phone_book.services;

import com.app.phone_book.models.Contact;
import com.app.phone_book.models.Group;
import com.app.phone_book.repositories.ContactRepository;
import com.app.phone_book.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Page<Group> getAllGroups(int page, int size) {
        return groupRepository.findAll(PageRequest.of(page, size));
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group getGroupById(UUID id) {
        return groupRepository.findById(id);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}

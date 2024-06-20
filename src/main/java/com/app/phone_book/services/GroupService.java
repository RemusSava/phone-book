package com.app.phone_book.services;

import com.app.phone_book.models.Contact;
import com.app.phone_book.models.Group;
import com.app.phone_book.repositories.ContactRepository;
import com.app.phone_book.repositories.GroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Page<Group> getAllGroups(int page, int size) {
        return groupRepository.findAll(PageRequest.of(page, size));
    }

    public List<Group> getAllGroupsWithoutPagination() {
        return groupRepository.findAll();
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group getGroupById(UUID id) {
        return groupRepository.findById(id);
    }

    @Transactional
    public void deleteGroup(UUID id) {
        groupRepository.deleteById(id);
    }

    public long count() {
        return groupRepository.count();
    }
}

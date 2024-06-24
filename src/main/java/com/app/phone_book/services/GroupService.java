package com.app.phone_book.services;

import com.app.phone_book.models.Group;
import com.app.phone_book.repositories.GroupRepository;
import com.app.phone_book.specifications.GroupSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Group> getFilteredUsers(String name, PageRequest pageRequest) {
        Specification<Group> spec = GroupSpecification.filterByCriteria(name);
        return groupRepository.findAll(spec, pageRequest);
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

    public Group getGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    @Transactional
    public void deleteGroup(UUID id) {
        groupRepository.deleteById(id);
    }

    public long count() {
        return groupRepository.count();
    }
}

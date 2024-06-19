package com.app.phone_book.controllers;

import com.app.phone_book.models.Contact;
import com.app.phone_book.models.Group;
import com.app.phone_book.services.ContactService;
import com.app.phone_book.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public String getAllGroups(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size, Model model) {
        Page<Group> groupPage = groupService.getAllGroups(page, size);
        model.addAttribute("groups", groupPage.getContent());
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", groupPage.getTotalPages());

        return "pages/group/list";
    }

    @PostMapping("/add")
    public String addGroup(@ModelAttribute("group") Group group) {
        groupService.saveGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Group getContactById(@PathVariable UUID id) {
        return groupService.getGroupById(id);
    }

    @PostMapping("/edit")
    public String editGroup(@ModelAttribute("group") Group group) {
        groupService.saveGroup(group);
        return "redirect:/groups";
    }

    @PostMapping("/delete/{id}")
    public String deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);
        return "redirect:/groups";
    }
}

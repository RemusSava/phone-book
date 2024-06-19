package com.app.phone_book.controllers;

import com.app.phone_book.models.Contact;
import com.app.phone_book.models.Group;
import com.app.phone_book.security.JwtUtil;
import com.app.phone_book.services.ContactService;
import com.app.phone_book.services.GroupService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public String getAllContacts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size, Model model, HttpServletRequest request) {
        String token = jwtUtil.extractJwtFromCookies(request);
        Page<Contact> contactPage = contactService.getAllContacts(page, size, token);
        List<Group> groups = groupService.getAllGroupsWithoutPagination();

        model.addAttribute("groups", groups);
        model.addAttribute("contacts", contactPage.getContent());
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contactPage.getTotalPages());

        return "pages/contact/list";
    }

    @PostMapping("/add")
    public String addContact(@ModelAttribute("contact") Contact contact, @RequestParam("groupId") UUID groupId, HttpServletRequest request) {
        String token = jwtUtil.extractJwtFromCookies(request);
        contactService.saveContact(contact, token, groupId);
        return "redirect:/contacts";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Contact getContactById(@PathVariable UUID id) {
        return contactService.getContactById(id);
    }

    @PostMapping("/edit")
    public String editContact(@ModelAttribute("contact") Contact contact, @RequestParam("groupId") UUID groupId) {
        contactService.saveContact(contact, null, groupId);
        return "redirect:/contacts";
    }

    @PostMapping("/delete/{id}")
    public String deleteContact(@PathVariable UUID id) {
        contactService.deleteContact(id);
        return "redirect:/contacts";
    }
}

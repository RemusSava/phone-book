package com.app.phone_book.controllers;

import com.app.phone_book.models.Contact;
import com.app.phone_book.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public String getAllContacts(Model model) {
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "pages/contact/list";
    }

    @GetMapping("/add")
    public String showAddContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "contacts/add";
    }

    @PostMapping("/add")
    public String addContact(@ModelAttribute("contact") Contact contact) {
        contactService.saveContact(contact);
        return "redirect:/contacts";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Contact getContactById(@PathVariable UUID id) {
        return contactService.getContactById(id);
    }

    @PostMapping("/edit")
    public String editContact(@ModelAttribute("contact") Contact contact) {
        contactService.saveContact(contact);
        return "redirect:/contacts";
    }
}

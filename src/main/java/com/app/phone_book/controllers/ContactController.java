package com.app.phone_book.controllers;

import com.app.phone_book.models.Contact;
import com.app.phone_book.models.Group;
import com.app.phone_book.security.JwtUtil;
import com.app.phone_book.services.ContactService;
import com.app.phone_book.services.GroupService;
import com.app.phone_book.validators.AddContactForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for handling contact-related operations.
 */
@Controller
@RequestMapping("/contacts")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Retrieves all contacts with pagination.
     *
     * @param page   Page number
     * @param size   Page size
     * @param model  Model for adding attributes
     * @param request HTTP servlet request
     * @return String representing the view name for contacts list
     */
    @GetMapping
    public String getAllContacts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model, HttpServletRequest request) {
        try {
            String token = jwtUtil.extractJwtFromCookies(request);
            Page<Contact> contactPage = contactService.getAllContacts(page, size, token);
            List<Group> groups = groupService.getAllGroupsWithoutPagination();

            model.addAttribute("groups", groups);
            model.addAttribute("contacts", contactPage.getContent());
            model.addAttribute("size", size);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", contactPage.getTotalPages());

            if (contactPage.isEmpty() && page > 0 && size > 0) {
                return "redirect:/contacts";
            }

            return "pages/contact/list";
        } catch (Exception e) {
            logger.error("Exception occurred while loading contacts list: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }

    /**
     * Adds a new contact.
     *
     * @param contactData New contact to add
     * @param request HTTP servlet request
     * @return String representing the view name after adding the contact
     */
    @PostMapping("/add")
    public String addContact(@Valid AddContactForm contactData, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {

            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                redirectAttributes.addFlashAttribute("errors", errorMessages);
                return "redirect:/contacts";
            }

            String token = jwtUtil.extractJwtFromCookies(request);
            Contact contact = AddContactForm.convertToContact(contactData);
            contactService.saveContact(contact, token, contactData.getGroupId());
            return "redirect:/contacts";
        } catch (Exception e) {
            logger.error("Exception occurred while adding a contact: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }

    /**
     * Retrieves a contact by its ID.
     *
     * @param id ID of the contact to retrieve
     * @return Contact object as JSON response
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Contact getContactById(@PathVariable UUID id) {
        return contactService.getContactById(id);
    }

    /**
     * Edits an existing contact.
     *
     * @param contactData Updated contact information
     * @return String representing the view name after editing the contact
     */
    @PostMapping("/edit")
    public String editContact(@Valid AddContactForm contactData, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {

            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                redirectAttributes.addFlashAttribute("errors", errorMessages);
                return "redirect:/contacts";
            }

            Contact contact = AddContactForm.convertToContact(contactData);
            contact.setId(contactData.getId());
            contactService.saveContact(contact, null, contactData.getGroupId());
            return "redirect:/contacts";
        } catch (Exception e) {
            logger.error("Exception occurred while editing a contact: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }

    /**
     * Deletes a contact by its ID.
     *
     * @param id ID of the contact to delete
     * @return String representing the view name after deleting the contact
     */
    @PostMapping("/delete/{id}")
    public String deleteContact(@PathVariable UUID id) {
        try {
            contactService.deleteContact(id);
            return "redirect:/contacts";
        } catch (Exception e) {
            logger.error("Exception occurred while deleting a contact: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }
}

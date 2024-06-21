package com.app.phone_book.controllers;

import com.app.phone_book.models.Group;
import com.app.phone_book.services.GroupService;
import com.app.phone_book.validators.AddGroupForm;
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
 * Controller class for handling group-related operations.
 */
@Controller
@RequestMapping("/groups")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    /**
     * Retrieves all groups with pagination.
     *
     * @param page   Page number
     * @param size   Page size
     * @param model  Model for adding attributes
     * @return String representing the view name for group list
     */
    @GetMapping
    public String getAllGroups(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size, Model model) {
        try {
            Page<Group> groupPage = groupService.getAllGroups(page, size);
            model.addAttribute("groups", groupPage.getContent());
            model.addAttribute("size", size);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", groupPage.getTotalPages());

            if (groupPage.isEmpty() && page > 0 && size > 0) {
                return "redirect:/groups";
            }

            return "pages/group/list";

        } catch (Exception e) {
            logger.error("Exception occurred while retrieving groups: {}", e.getMessage());
            model.addAttribute("error", "Failed to fetch groups. Please try again later.");
            return "error"; // Redirect to error page
        }
    }

    /**
     * Adds a new group.
     *
     * @param groupData  Group object to be added
     * @return String representing the view name after adding group
     */
    @PostMapping("/add")
    public String addGroup(@Valid AddGroupForm groupData, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {

            Group findGroup = groupService.getGroupByName(groupData.getName());
            if(findGroup != null){
                redirectAttributes.addFlashAttribute("errors", "This name already exists");
                return "redirect:/groups";
            }

            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                redirectAttributes.addFlashAttribute("errors", errorMessages);
                return "redirect:/groups";
            }

            Group group = AddGroupForm.convertToGroup(groupData);
            groupService.saveGroup(group);
            return "redirect:/groups";
        } catch (Exception e) {
            logger.error("Exception occurred while adding group: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }

    /**
     * Retrieves a group by ID.
     *
     * @param id  Group ID
     * @return Group object in JSON format
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Group getGroupById(@PathVariable UUID id) {
        return groupService.getGroupById(id);
    }

    /**
     * Edits an existing group.
     *
     * @param groupData  Group object to be edited
     * @return String representing the view name after editing group
     */
    @PostMapping("/edit")
    public String editGroup(@Valid AddGroupForm groupData, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {

            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                redirectAttributes.addFlashAttribute("errors", errorMessages);
                return "redirect:/groups";
            }

            Group findGroup = groupService.getGroupByName(groupData.getName());
            if (findGroup != null && !findGroup.getId().equals(groupData.getId())) {
                redirectAttributes.addFlashAttribute("errors", "This name already exists");
                return "redirect:/groups";
            }

            Group group = AddGroupForm.convertToGroup(groupData);
            group.setId(groupData.getId());
            groupService.saveGroup(group);
            return "redirect:/groups";
        } catch (Exception e) {
            logger.error("Exception occurred while editing group: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }

    /**
     * Deletes a group by ID.
     *
     * @param id  Group ID
     * @return String representing the view name after deleting group
     */
    @PostMapping("/delete/{id}")
    public String deleteGroup(@PathVariable UUID id) {
        try {
            groupService.deleteGroup(id);
            return "redirect:/groups";
        } catch (Exception e) {
            logger.error("Exception occurred while deleting group: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }
}

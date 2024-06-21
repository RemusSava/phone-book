package com.app.phone_book.controllers;

import com.app.phone_book.models.Role;
import com.app.phone_book.models.User;
import com.app.phone_book.services.RoleService;
import com.app.phone_book.services.UserService;
import com.app.phone_book.validators.AddUserForm;
import com.app.phone_book.validators.EditUserForm;
import com.app.phone_book.validators.RegisterForm;
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
 * Controller class for handling user-related operations.
 */
@Controller
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * Displays the registration form.
     *
     * @return String representing the view name for registration page
     */
    @GetMapping("/register")
    public String register() {
        return "pages/register";
    }

    /**
     * Handles registration form submission.
     *
     * @param registerForm        Registration form data
     * @param bindingResult       Binding result for validation errors
     * @param redirectAttributes  Redirect attributes for flash messages
     * @param model               Model for adding attributes
     * @return String representing the view name after registration attempt
     */
    @PostMapping("/register")
    public String register(@Valid RegisterForm registerForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes, Model model) {
        try {

            User user = userService.findByEmail(registerForm.getEmail());
            if(user != null){
                model.addAttribute("errors", "The Email already exists");
                return "pages/register";
            }

            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                model.addAttribute("errors", errorMessages);
                return "pages/register";
            }

            userService.register(registerForm);
            redirectAttributes.addFlashAttribute("success", "Registration successful");
            return "redirect:/login";   // Redirect to login page after successful registration

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "pages/register";
        }
    }

    /**
     * Retrieves all users with pagination.
     *
     * @param page   Page number
     * @param size   Page size
     * @param model  Model for adding attributes
     * @return String representing the view name for user list
     */
    @GetMapping("/admin/users")
    public String getAllUsers(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "1") int size, Model model) {
        try {
            Page<User> userPage = userService.getAllUsers(page, size);
            List<Role> roles = roleService.getAll();

            model.addAttribute("roles", roles);
            model.addAttribute("users", userPage.getContent());
            model.addAttribute("size", size);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", userPage.getTotalPages());

            if (userPage.isEmpty() && page > 0 && size > 0) {
                return "redirect:/admin/users";
            }

            return "pages/user/list";

        } catch (Exception e) {
            logger.error("Exception occurred while retrieving users: {}", e.getMessage());
            model.addAttribute("error", "Failed to fetch users. Please try again later.");
            return "error"; // Assuming you have an error.html page to display generic errors
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id  User ID
     * @return User object in JSON format
     */
    @GetMapping("/admin/users/{id}")
    @ResponseBody
    public User getUserById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    /**
     * Adds a new user.
     *
     * @param formData
     * @return String representing the view name after adding user
     */
    @PostMapping("/admin/users/add")
    public String addUser(@Valid EditUserForm formData, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {

            User findUser = userService.findByEmail(formData.getEmail());
            if(findUser != null){
                redirectAttributes.addFlashAttribute("errors", List.of("The Email already exists"));
                return "redirect:/admin/users";

            }

            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                redirectAttributes.addFlashAttribute("errors", errorMessages);
                return "redirect:/admin/users";
            }

            User user = EditUserForm.convertToUser(formData);
            userService.save(user, formData.getRoleId());
            return "redirect:/admin/users";
        } catch (Exception e) {
            // Log the exception
            logger.error("Exception occurred while adding user: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }

    /**
     * Edits an existing user.
     *
     * @param formData    AddUserForm object to be edited
     * @return String representing the view name after editing user
     */
    @PostMapping("/admin/users/edit")
    public String editUser(@Valid AddUserForm formData, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {


            User findUser = userService.findByEmail(formData.getEmail());
            if(findUser != null){
                redirectAttributes.addFlashAttribute("errors", List.of("The Email already exists"));
                return "redirect:/admin/users";

            }

            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                redirectAttributes.addFlashAttribute("errors", errorMessages);
                return "redirect:/admin/users";
            }


            User user = AddUserForm.convertToUser(formData);
            userService.save(user, formData.getRoleId());
            return "redirect:/admin/users";
        } catch (Exception e) {

            logger.error("Exception occurred while editing user: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param id  User ID
     * @return String representing the view name after deleting user
     */
    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteById(id);
            return "redirect:/admin/users";
        } catch (Exception e) {
            logger.error("Exception occurred while deleting user: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }
}

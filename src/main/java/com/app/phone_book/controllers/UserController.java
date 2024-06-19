package com.app.phone_book.controllers;

import com.app.phone_book.models.User;
import com.app.phone_book.services.UserService;
import com.app.phone_book.validators.RegisterForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() {
        return "pages/register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterForm registerForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("errors", errorMessages);
            return "pages/register";
        }

        try {
            userService.register(registerForm);
            redirectAttributes.addFlashAttribute("success", "Registration successful");
            return "redirect:/login";   // Redirect to login page after successful registration
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "pages/register";
        }
    }
}

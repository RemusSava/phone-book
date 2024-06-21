package com.app.phone_book.controllers;

import com.app.phone_book.security.JwtUtil;
import com.app.phone_book.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for handling authentication-related operations.
 */
@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    /**
     * Displays the login page.
     *
     * @return String representing the view name for login page
     */
    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    /**
     * Logs out the current user.
     *
     * @param response HTTP servlet response
     * @return String representing the view name or redirect after logout
     */
    @GetMapping("/auth/logout")
    public String logout(HttpServletResponse response) {
        return authService.logout(response);
    }

    /**
     * Redirects to the login page.
     *
     * @param response HTTP servlet response
     * @return String representing the redirect path to the login page
     */
    @GetMapping("/auth/login")
    public String redirectLogin(HttpServletResponse response) {
        return "redirect:/login";
    }

    /**
     * Handles user login authentication.
     *
     * @param email    User's email
     * @param password User's password
     * @param response HTTP servlet response
     * @param model    Model for adding attributes
     * @return String representing the view name or redirect after login attempt
     */
    @PostMapping("/auth/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpServletResponse response, Model model) {
        try {
            String redirect = authService.authenticateAndSetToken(email, password, response);
            if (redirect == null) {
                model.addAttribute("error", "Invalid credentials");
                return "pages/login";
            }

            return "redirect:/login";
        } catch (Exception e) {
            logger.error("Authentication failed!", e);
            return "error";
        }
    }
}

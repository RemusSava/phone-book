package com.app.phone_book.controllers;

import com.app.phone_book.security.JwtUtil;
import com.app.phone_book.services.AuthService;
import com.app.phone_book.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpServletResponse response, Model model) {
        try {
            String redirect = authService.authenticateAndSetToken(email, password, response);
            if (redirect == null) {
                model.addAttribute("error", "Invalid credentials");
                return "login";
            }

            return redirect;
        } catch (Exception e) {
            logger.error("Authentication failed!", e);
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}
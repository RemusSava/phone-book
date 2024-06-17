package com.app.phone_book.services;

import com.app.phone_book.models.User;
import com.app.phone_book.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public String authenticateAndSetToken(String email, String password, HttpServletResponse response) {
        User authenticatedUser = userService.authenticate(email, password);

        if(authenticatedUser == null){
            return null;
        }

        String token = jwtUtil.generateToken(authenticatedUser.getEmail(), authenticatedUser.getPassword());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/dashboard";
    }
}

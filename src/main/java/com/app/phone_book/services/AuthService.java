package com.app.phone_book.services;

import com.app.phone_book.models.Role;
import com.app.phone_book.models.User;
import com.app.phone_book.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    public String authenticateAndSetToken(String email, String password, HttpServletResponse response) {
        User authenticatedUser = userService.authenticate(email, password);

        if(authenticatedUser == null){
            return null;
        }

        System.out.println(authenticatedUser.getRoles());
        List<String> roles = authenticatedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String token = jwtUtil.generateToken(authenticatedUser.getEmail(), authenticatedUser.getPassword(), roles);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/dashboard";
    }

    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // This will delete the cookie
        response.addCookie(cookie);

        return "redirect:/login";
    }
}

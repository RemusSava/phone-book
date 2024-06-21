package com.app.phone_book.filters;

import com.app.phone_book.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private static final List<String> EXCLUDE_URLS = List.of("/login", "/auth/login", "/register");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String jwt = getJwtFromCookie(request);

        if (jwt != null) {
            try {
                Claims claims = jwtUtil.extractAllClaims(jwt);
                String username = claims.getSubject();

                if (jwtUtil.isTokenExpired(jwt)) {
                    response.sendRedirect("/login");
                    return;
                }

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.validateToken(jwt, username)) {
                        String role = jwtUtil.extractRole(jwt);
                        authenticateUser(username, role, request);
                    }
                }

                if (EXCLUDE_URLS.contains(requestURI)) {
                    response.sendRedirect("/dashboard");
                    return;
                }

            } catch (Exception e) {
                // Invalid token, handle the exception
                logger.error("Error processing JWT token: {}");
                response.sendRedirect("/login");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void authenticateUser(String username, String role, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}

package com.app.phone_book.controllers;

import com.app.phone_book.security.JwtUtil;
import com.app.phone_book.services.ContactService;
import com.app.phone_book.services.GroupService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling dashboard-related operations.
 */
@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Displays the home dashboard.
     *
     * @param model Model for adding attributes
     * @return String representing the view name for home dashboard
     */
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        try {
            String token = jwtUtil.extractJwtFromCookies(request);
            model.addAttribute("totalContacts", contactService.count(token));
            model.addAttribute("totalGroups", groupService.count());
            return "pages/dashboard";
        } catch (Exception e) {
            logger.error("Exception occurred while loading home dashboard: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }

    /**
     * Displays the dashboard.
     *
     * @param model Model for adding attributes
     * @return String representing the view name for dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) {
        try {
            String token = jwtUtil.extractJwtFromCookies(request);
            model.addAttribute("totalContacts", contactService.count(token));
            model.addAttribute("totalGroups", groupService.count());
            return "pages/dashboard";
        } catch (Exception e) {
            logger.error("Exception occurred while loading dashboard: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }

    /**
     * Displays the admin dashboard.
     *
     * @param model Model for adding attributes
     * @return String representing the view name for admin dashboard
     */
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, HttpServletRequest request) {
        try {
            String token = jwtUtil.extractJwtFromCookies(request);
            model.addAttribute("totalContacts", contactService.count(token));
            model.addAttribute("totalGroups", groupService.count());
            return "pages/dashboard";
        } catch (Exception e) {
            logger.error("Exception occurred while loading admin dashboard: {}", e.getMessage());
            return "error"; // Redirect to error page
        }
    }
}

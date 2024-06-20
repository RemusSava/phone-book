package com.app.phone_book.controllers;

import com.app.phone_book.services.ContactService;
import com.app.phone_book.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {


    @Autowired
    private ContactService contactService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalContacts", contactService.count());
        model.addAttribute("totalGroups", groupService.count());
        return "pages/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalContacts", contactService.count());
        model.addAttribute("totalGroups", groupService.count());
        return "pages/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalContacts", contactService.count());
        model.addAttribute("totalGroups", groupService.count());
        return "pages/dashboard";
    }
}

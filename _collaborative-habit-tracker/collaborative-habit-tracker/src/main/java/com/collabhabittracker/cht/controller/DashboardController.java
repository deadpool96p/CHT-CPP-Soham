// src/main/java/com/collabhabittracker/cht/controller/DashboardController.java
package com.collabhabittracker.cht.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("title", "Dashboard - HabitTracker");
        model.addAttribute("username", username);
        model.addAttribute("welcomeMessage", "Welcome to your habit dashboard, " + username + "!");
        return "dashboard";
    }
}
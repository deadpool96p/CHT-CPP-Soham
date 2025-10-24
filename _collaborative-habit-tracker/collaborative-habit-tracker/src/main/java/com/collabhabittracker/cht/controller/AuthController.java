package com.collabhabittracker.cht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @GetMapping("/auth/login")
    public String loginPage(Model model) {
        return "auth/login";
    }

    @GetMapping("/auth/register")
    public String registerPage(Model model) {
        return "auth/register";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password, 
                       HttpSession session) {
        // For demo purposes, accept any login
        // In real app, validate against database
        if (email != null && !email.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
            session.setAttribute("userEmail", email);
            session.setAttribute("userName", email.split("@")[0]);
            return "redirect:/";
        }
        
        return "redirect:/auth/login?error=true";
    }

    @PostMapping("/auth/register")
    public String register(@RequestParam String fullName,
                          @RequestParam String email,
                          @RequestParam String password,
                          HttpSession session) {
        // For demo purposes, accept any registration
        // In real app, save to database and validate
        if (email != null && !email.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
            session.setAttribute("userEmail", email);
            session.setAttribute("userName", fullName != null ? fullName : email.split("@")[0]);
            return "redirect:/";
        }
        
        return "redirect:/auth/register?error=true";
    }

    @GetMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
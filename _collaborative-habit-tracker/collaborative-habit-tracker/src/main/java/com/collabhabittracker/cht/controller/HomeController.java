package com.collabhabittracker.cht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;
import java.util.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        // Check if user is logged in (simulated)
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            // For demo, create a default user session
            userEmail = "user@example.com";
            session.setAttribute("userEmail", userEmail);
            session.setAttribute("userName", "Demo User");
        }
        
        // Simulate getting user data from database
        Map<String, Object> userData = getSimulatedUserData(userEmail);
        
        // Prepare model data
        model.addAttribute("user", userData);
        model.addAttribute("todayHabits", getSimulatedTodayHabits());
        model.addAttribute("completedToday", 5);
        model.addAttribute("totalHabits", 12);
        model.addAttribute("dueToday", 3);
        model.addAttribute("successRate", 85);
        model.addAttribute("recentActivities", getSimulatedRecentActivities());
        
        return "home";
    }

    @GetMapping("/habits")
    public String habits(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/auth/login";
        }
        
        model.addAttribute("user", getSimulatedUserData(userEmail));
        model.addAttribute("habits", getAllSimulatedHabits());
        return "habits";
    }

    @GetMapping("/create-habit")
    public String createHabit(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/auth/login";
        }
        
        model.addAttribute("user", getSimulatedUserData(userEmail));
        return "create-habit";
    }

    @GetMapping("/groups")
    public String groups(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/auth/login";
        }
        
        model.addAttribute("user", getSimulatedUserData(userEmail));
        return "groups";
    }

    @GetMapping("/analytics")
    public String analytics(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/auth/login";
        }
        
        model.addAttribute("user", getSimulatedUserData(userEmail));
        return "analytics";
    }

    @GetMapping("/about")
    public String about(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            userEmail = "guest";
        }
        
        model.addAttribute("user", getSimulatedUserData(userEmail));
        return "about";
    }
 @GetMapping("/profile")
public String profile(Model model, HttpSession session) {
    String userEmail = (String) session.getAttribute("userEmail");
    if (userEmail == null) {
        return "redirect:/auth/login";
    }
    
    model.addAttribute("user", getSimulatedUserData(userEmail));
    return "profile";
}

@GetMapping("/settings")
public String settings(Model model, HttpSession session) {
    String userEmail = (String) session.getAttribute("userEmail");
    if (userEmail == null) {
        return "redirect:/auth/login";
    }
    
    model.addAttribute("user", getSimulatedUserData(userEmail));
    return "settings"; // You can create settings.html later
}

@GetMapping("/achievements")
public String achievements(Model model, HttpSession session) {
    String userEmail = (String) session.getAttribute("userEmail");
    if (userEmail == null) {
        return "redirect:/auth/login";
    }
    
    model.addAttribute("user", getSimulatedUserData(userEmail));
    return "achievements"; // You can create achievements.html later
}

    @PostMapping("/api/habits/{habitId}/complete")
    @ResponseBody
    public ResponseEntity<String> completeHabit(@PathVariable String habitId, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        // In real app, save to database
        System.out.println("Habit " + habitId + " completed by user " + userEmail);
        
        return ResponseEntity.ok("Habit completed successfully");
    }

    @PostMapping("/api/habits/create")
    @ResponseBody
    public ResponseEntity<String> createHabit(String name, String description, String frequency, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        
        // In real app, save to database
        System.out.println("New habit created: " + name + " for user " + userEmail);
        
        return ResponseEntity.ok("Habit created successfully");
    }

    // Simulated data methods (replace with database calls in real app)
    private Map<String, Object> getSimulatedUserData(String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("name", email.split("@")[0]);
        user.put("joinDate", "2024-01-01");
        user.put("streak", 42);
        return user;
    }

    private List<Map<String, Object>> getSimulatedTodayHabits() {
        List<Map<String, Object>> habits = new ArrayList<>();
        
        Map<String, Object> habit1 = new HashMap<>();
        habit1.put("id", "1");
        habit1.put("name", "Morning Meditation");
        habit1.put("description", "10 minutes of mindfulness meditation");
        habit1.put("icon", "fa-brain");
        habit1.put("completed", false);
        habits.add(habit1);
        
        Map<String, Object> habit2 = new HashMap<>();
        habit2.put("id", "2");
        habit2.put("name", "Evening Run");
        habit2.put("description", "30 minutes of running");
        habit2.put("icon", "fa-running");
        habit2.put("completed", false);
        habits.add(habit2);
        
        return habits;
    }

    private List<Map<String, Object>> getAllSimulatedHabits() {
        List<Map<String, Object>> habits = getSimulatedTodayHabits();
        
        Map<String, Object> habit3 = new HashMap<>();
        habit3.put("id", "3");
        habit3.put("name", "Reading");
        habit3.put("description", "Read 50 pages per week");
        habit3.put("icon", "fa-book");
        habit3.put("completed", true);
        habit3.put("frequency", "weekly");
        habit3.put("streak", 2);
        habits.add(habit3);
        
        return habits;
    }

    private List<Map<String, Object>> getSimulatedRecentActivities() {
        List<Map<String, Object>> activities = new ArrayList<>();
        
        Map<String, Object> activity1 = new HashMap<>();
        activity1.put("description", "Completed \"Evening Run\"");
        activity1.put("time", "2 hours ago");
        activity1.put("icon", "fa-check-circle");
        activities.add(activity1);
        
        Map<String, Object> activity2 = new HashMap<>();
        activity2.put("description", "Joined \"Fitness Group\"");
        activity2.put("time", "3 hours ago");
        activity2.put("icon", "fa-users");
        activities.add(activity2);
        
        Map<String, Object> activity3 = new HashMap<>();
        activity3.put("description", "7-day streak achieved");
        activity3.put("time", "Monday");
        activity3.put("icon", "fa-fire");
        activities.add(activity3);
        
        return activities;
    }
}
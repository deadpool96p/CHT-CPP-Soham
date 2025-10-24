package com.collabhabittracker.cht.controller;

import com.collabhabittracker.cht.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {
    
    @Autowired
    private OllamaService ollamaService;
    
    @PostMapping("/recommendations")
    public ResponseEntity<Map<String, String>> getHabitRecommendations(
            @RequestBody RecommendationRequest request) {
        
        try {
            String recommendation = ollamaService.getHabitRecommendation(
                request.getUserContext(), 
                request.getHabitHistory()
            );
            
            return ResponseEntity.ok(Map.of("recommendation", recommendation));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("recommendation", 
                "I'm having trouble generating recommendations right now. Please try again later."));
        }
    }
    
    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> getChatResponse(
            @RequestBody ChatRequest request) {
        
        try {
            String response = ollamaService.getHabitChat(
                request.getMessage(), 
                request.getChatHistory()
            );
            
            return ResponseEntity.ok(Map.of("response", response));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("response", 
                "I'm having trouble responding right now. Please try again in a moment."));
        }
    }
    @GetMapping("/test-ollama")
public ResponseEntity<Map<String, String>> testOllama() {
    try {
        String testResponse = ollamaService.getHabitChat("Hello, are you working?", "");
        return ResponseEntity.ok(Map.of("status", "success", "response", testResponse));
    } catch (Exception e) {
        return ResponseEntity.ok(Map.of("status", "error", "message", e.getMessage()));
    }
}
    
    // Request DTOs
    public static class RecommendationRequest {
        private String userContext;
        private String habitHistory;
        
        public String getUserContext() { return userContext; }
        public void setUserContext(String userContext) { this.userContext = userContext; }
        public String getHabitHistory() { return habitHistory; }
        public void setHabitHistory(String habitHistory) { this.habitHistory = habitHistory; }
    }
    
    public static class ChatRequest {
        private String message;
        private String chatHistory;
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getChatHistory() { return chatHistory; }
        public void setChatHistory(String chatHistory) { this.chatHistory = chatHistory; }
    }
}
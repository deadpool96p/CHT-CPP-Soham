package com.collabhabittracker.cht.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

@Service
public class OllamaService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    public OllamaService(WebClient.Builder webClientBuilder) {
        this.objectMapper = new ObjectMapper();
        // Build WebClient WITHOUT baseUrl in the builder since we're using full URLs
        this.webClient = webClientBuilder.build();
        System.out.println("🤖 OllamaService initialized with base URL: " + ollamaBaseUrl);
    }

    public String getHabitRecommendation(String userContext, String habitHistory) {
        String prompt = String.format(
            "As a habit tracking assistant, provide personalized habit recommendations based on:\n" +
            "User Context: %s\n" +
            "Habit History: %s\n\n" +
            "Provide 3-5 specific, actionable habit suggestions.",
            userContext, habitHistory
        );

        return callOllama(prompt);
    }

    public String getHabitChat(String message, String chatHistory) {
        // Build prompt with chat history context
        String prompt = buildChatPrompt(message, chatHistory);
        return callOllama(prompt);
    }

    private String buildChatPrompt(String message, String chatHistory) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a helpful habit tracking assistant. ");
        
        if (chatHistory != null && !chatHistory.trim().isEmpty()) {
            prompt.append("Here is our conversation history:\n");
            prompt.append(chatHistory);
            prompt.append("\n\n");
        } else {
            prompt.append("This is a new conversation.\n\n");
        }
        
        prompt.append("Please respond to this message: ").append(message);
        prompt.append("\n\nKeep your response helpful and focused on habits.");
        
        return prompt.toString();
    }

    private String callOllama(String prompt) {
    try {
        System.out.println("=== OLLAMA DEBUG ===");
        System.out.println("📨 Sending request to: " + ollamaBaseUrl + "/api/generate");
        System.out.println("📝 Prompt: " + prompt.substring(0, Math.min(prompt.length(), 200)) + "...");
        
        OllamaRequest request = new OllamaRequest("mistral", prompt, false);
        System.out.println("🔧 Request object: " + objectMapper.writeValueAsString(request));

        long startTime = System.currentTimeMillis();
        
        String response = webClient.post()
                .uri(ollamaBaseUrl + "/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        long endTime = System.currentTimeMillis();
        System.out.println("⏱️ Response time: " + (endTime - startTime) + "ms");

        if (response != null) {
            System.out.println("✅ Raw response: " + response);
            JsonNode jsonNode = objectMapper.readTree(response);
            if (jsonNode.has("response")) {
                String aiResponse = jsonNode.get("response").asText();
                System.out.println("🤖 AI Response: " + aiResponse);
                return aiResponse;
            } else {
                System.out.println("❌ No 'response' field in JSON: " + response);
            }
        } else {
            System.out.println("❌ Response is null");
        }
        
        return getFallbackResponse();
        
    } catch (Exception e) {
        System.err.println("💥 Exception: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        e.printStackTrace();
        return getFallbackResponse();
    }
}

    private String getFallbackResponse() {
        return "I'm here to help with your habit journey! It looks like there might be a temporary issue with the AI service. Please try again in a moment.";
    }

    // Request DTO
    public static class OllamaRequest {
        private String model;
        private String prompt;
        private boolean stream;

        public OllamaRequest(String model, String prompt, boolean stream) {
            this.model = model;
            this.prompt = prompt;
            this.stream = stream;
        }

        public String getModel() { return model; }
        public String getPrompt() { return prompt; }
        public boolean isStream() { return stream; }
        
        public void setModel(String model) { this.model = model; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
        public void setStream(boolean stream) { this.stream = stream; }
    }
}
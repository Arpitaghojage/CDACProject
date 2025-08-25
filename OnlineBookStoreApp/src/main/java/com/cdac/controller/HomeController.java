package com.cdac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Online Book Store API");
        response.put("version", "1.0.0");
        response.put("status", "running");
        response.put("swagger-ui", "/swagger-ui/index.html");
        response.put("public-endpoints", new String[]{
            "GET /books - Get all books",
            "GET /categories - Get all categories",
            "POST /users/signup - User registration",
            "POST /users/signin - User login"
        });
        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Application is running successfully");
        return response;
    }
} 
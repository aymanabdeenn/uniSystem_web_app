package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.services.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*") // Allow requests from any origin
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final AIService aiService;

    @Autowired
    public ChatController(AIService aiService){
        this.aiService = aiService;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String , String>> sendMessage(@RequestBody Map<String , String> payload){
        Map<String , String> aiResponse = aiService.getResponse(payload);
        return ResponseEntity.ok(aiResponse);
    }

}

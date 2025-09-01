package com.example.uniSystem_web_app.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AIService {

    private final String python_server_URL = "http://127.0.0.1:8000/chat";

    RestTemplate restTemplate = new RestTemplate();

    public Map<String , String> getResponse(Map<String , String> prompt){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(prompt, headers);

        try {
            Map<String, String> apiResponse = restTemplate.postForObject(python_server_URL, request, Map.class);
            if (apiResponse != null && !apiResponse.isEmpty()) {
                String generatedText = apiResponse.get("response");
                return Map.of("response", generatedText);
            } else {
                return Map.of("response", "No response from model");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("response", "Error: " + e.getMessage());
        }
    }

}

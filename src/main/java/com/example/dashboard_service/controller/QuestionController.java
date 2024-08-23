package com.example.dashboard_service.controller;

import com.example.dashboard_service.config.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")  // Adjusted base path
public class QuestionController {

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/questions")
    public ResponseEntity<List<ServiceConfig.Question>> getQuestions() {
        return ResponseEntity.ok(serviceConfig.getQuestions());
    }

    @PostMapping("/execute")
    public ResponseEntity<Object> executeQuery(@RequestBody Map<String, String> request) {
        String questionText = request.get("question");

        if (questionText == null || questionText.isEmpty()) {
            return ResponseEntity.badRequest().body("Question is required");
        }

        try {
            // Find the matching question in the configuration
            ServiceConfig.Question selectedQuestion = serviceConfig.getQuestions().stream()
                    .filter(q -> q.getQuestion().equals(questionText))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            // Get the query from the selected question
            String query = selectedQuestion.getQuery();

            // Replace this URL with your Elasticsearch endpoint
            String esUrl = "http://localhost:9200/elkdemoindex_v3/_search";

            // Set headers for the request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(query, headers);

            // Execute the Elasticsearch query
            ResponseEntity<Object> response = restTemplate.exchange(esUrl, HttpMethod.POST, entity, Object.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            // Log the exception (consider using a logging framework)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}

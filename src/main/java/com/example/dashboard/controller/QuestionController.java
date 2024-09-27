package com.example.dashboard.controller;

import com.example.dashboard.config.ServiceConfig;
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

@RestController
@RequestMapping("/api")  // Adjusted base path
public class QuestionController {

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private RestTemplate restTemplate;

    @CrossOrigin(origins = "*")
    @GetMapping("/questions")
    public ResponseEntity<List<ServiceConfig.Question>> getQuestions() {
        return ResponseEntity.ok(serviceConfig.getQuestions());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/execute")
    public ResponseEntity<Object> executeQuery(@RequestParam("id") int id) {
        try {
            // Find the matching question in the configuration by ID
            ServiceConfig.Question selectedQuestion = serviceConfig.getQuestions().stream()
                    .filter(q -> q.getId() == id)
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

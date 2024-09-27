package com.example.dashboard.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class CountUtility {
    private final RestTemplate restTemplate;

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUrl;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    public CountUtility(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String countLogs(String requestURI, String httpMethod, String countMessage) {

        String query = String.format(
                "{ \"query\": { \"query_string\": { \"query\": \"\\\"This method is successful -> requestURI: %s\\\" AND \\\"httpMethod: %s\\\" AND \\\"Response Status: 200\\\"\" } } }",
                requestURI, httpMethod
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getBasicAuthHeader(username, password));
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                elasticsearchUrl + "/elkdemoindex_v3/_count",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        try {
            // Parse the JSON response to extract the count value
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            return rootNode.get("count").asText() +" "+ countMessage;
        } catch (Exception e) {
            return "service is down to count";
//            e.printStackTrace();
            // Handle the error appropriately in your application
//            return null;
        }
    }

    private static String getBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }
}

package com.example.dashboard.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class CurlExecutionController {

    private final WebClient webClient;

    public CurlExecutionController(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @PostMapping("/execute")
    public Mono<String> executeCurl(@RequestBody String curlCommand) throws JSONException {
        // Extract relevant information from the cURL command
        String url = extractUrl(curlCommand);
        String method = extractMethod(curlCommand);
        String jsonData = extractJsonData(curlCommand);
        HttpHeaders headers = extractHeaders(curlCommand);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Execute the request using RestTemplate
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonData, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.valueOf(method), requestEntity, String.class);

        return Mono.just(Objects.requireNonNull(responseEntity.getBody()));
    }

    private String extractUrl(String curlCommand) {
        // Regular expression to match the URL pattern
        Pattern urlPattern = Pattern.compile("https?://\\S+");
        Matcher urlMatcher = urlPattern.matcher(curlCommand);
        return urlMatcher.find() ? urlMatcher.group() : null;
    }

    private String extractMethod(String curlCommand) {
        // Regular expression to match the HTTP method pattern (case-insensitive)
        Pattern methodPattern = Pattern.compile("-X\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher methodMatcher = methodPattern.matcher(curlCommand);
        return methodMatcher.find() ? methodMatcher.group(1).toUpperCase() : "GET"; // Default to GET if not specified
    }

    public String extractJsonData(String jsonString) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String curlCommand = jsonObject.getString("curlCommand");

            // Updated pattern to handle potential newlines within JSON data
            Pattern jsonDataPattern = Pattern.compile("\\s*-(d|\\-\\-data)\\s+([\"'])(.*?)\\2", Pattern.CASE_INSENSITIVE);
            Matcher jsonDataMatcher = jsonDataPattern.matcher(curlCommand);

            if (jsonDataMatcher.find()) {
                System.out.println("group(1): "+ jsonDataMatcher.group(1));
                System.out.println("group(2): "+ jsonDataMatcher.group(2));
                System.out.println("group(3): "+ jsonDataMatcher.group(3));
                return jsonDataMatcher.group(1); // Extract the actual JSON data
            } else {
                System.out.println("No data found in cURL command");
                return null;
            }
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }

    private HttpHeaders extractHeaders(String curlCommand) {
        // Regular expression to match headers (-H "Header: value")
        Pattern headerPattern = Pattern.compile("-H\\s+\"(.*?):\\s*(.*?)\"", Pattern.CASE_INSENSITIVE);
        Matcher headerMatcher = headerPattern.matcher(curlCommand);

        HttpHeaders headers = new HttpHeaders();
        while (headerMatcher.find()) {
            String key = headerMatcher.group(1);
            String value = headerMatcher.group(2);
            headers.add(key, value);
        }
        return headers;
    }
}

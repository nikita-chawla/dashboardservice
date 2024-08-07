package com.example.dashboard_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public String processInput(String userInput) throws Exception {
        String url = "https://api.openai.com/v1/engines/davinci-codex/completions";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + openaiApiKey);

        String json = "{\"prompt\": \"Convert this user input to an Elasticsearch query: " + userInput + "\", \"max_tokens\": 150}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);

        CloseableHttpResponse response = client.execute(httpPost);
        String responseBody = EntityUtils.toString(response.getEntity());
        client.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseJson = mapper.readTree(responseBody);
        return responseJson.get("choices").get(0).get("text").asText().trim();
    }
}

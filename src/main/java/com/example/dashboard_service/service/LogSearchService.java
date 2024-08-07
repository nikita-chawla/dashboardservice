package com.example.dashboard_service.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private TextProcessingService textProcessingService;

    public String searchLogs(String queryText) throws Exception {
        // Process the human text
        String[] sentences = textProcessingService.detectSentences(queryText);
        // Use the processed text to build the query
        String processedQuery = String.join(" ", sentences);

        SearchRequest searchRequest = new SearchRequest("elkdemoindex"); // Replace "logs" with your index name
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.queryStringQuery(processedQuery));
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse.toString();
    }
}


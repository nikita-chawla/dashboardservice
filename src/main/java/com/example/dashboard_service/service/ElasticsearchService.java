package com.example.dashboard_service.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticsearchService {

    @Autowired
    private RestHighLevelClient client;

    public String searchLogs(String keyword) throws IOException {
        SearchRequest searchRequest = new SearchRequest("elkdemoindex"); // Replace with your index name
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("message", keyword)); // Replace "message" with your log field
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse.toString();
    }
//    public SearchResponse searchLogs(String query) throws Exception {
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.wrapperQuery(query));
//
//        SearchRequest searchRequest = new SearchRequest("logs");
//        searchRequest.source(searchSourceBuilder);
//
//        return client.search(searchRequest, RequestOptions.DEFAULT);
//    }
}

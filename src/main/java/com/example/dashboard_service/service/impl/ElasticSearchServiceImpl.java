package com.example.dashboard_service.service.impl;

import com.example.dashboard_service.service.ElasticSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public String searchLogs(String keyword) throws IOException {
        SearchRequest searchRequest = new SearchRequest("elkdemoindex_v3"); // Replace with your index name
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("message", keyword)); // Replace "message" with your log field
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse.toString();
    }
}

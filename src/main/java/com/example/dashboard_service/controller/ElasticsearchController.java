package com.example.dashboard_service.controller;

import com.example.dashboard_service.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ElasticsearchController {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @CrossOrigin(origins = "*")
    @GetMapping("/search-logs")
    public String searchLogs(@RequestParam String keyword) {
        try {
            return elasticSearchService.searchLogs(keyword);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while searching logs: " + e.getMessage();
        }
    }
}


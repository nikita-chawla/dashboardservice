package com.example.dashboard_service.controller;

import com.example.dashboard_service.service.LogSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogSearchController {

    @Autowired
    private LogSearchService logSearchService;

    @GetMapping("/search-logs-new")
    public String searchLogs(@RequestParam String query) {
        try {
            return logSearchService.searchLogs(query);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}


package com.example.dashboard_service.service;

import java.io.IOException;

public interface ElasticSearchService {
    String searchLogs(String keyword) throws IOException;
}

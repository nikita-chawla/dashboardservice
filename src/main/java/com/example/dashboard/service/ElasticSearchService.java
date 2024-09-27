package com.example.dashboard.service;

import java.io.IOException;

public interface ElasticSearchService {
    String searchLogs(String keyword) throws IOException;
}

package com.example.dashboard.service.impl;

import com.example.dashboard.config.ServiceConfig;
import com.example.dashboard.model.Dashboard;
import com.example.dashboard.service.DashboardService;
import com.example.dashboard.util.CountUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final RestTemplate restTemplate;
    private final ServiceConfig serviceConfig;

    @Autowired
    CountUtility countUtility;

    @Autowired
    public DashboardServiceImpl(RestTemplate restTemplate, ServiceConfig serviceConfig) {
        this.restTemplate = restTemplate;
        this.serviceConfig = serviceConfig;
    }

    @Override
    public Dashboard getDashboardDetails() {
        String userCount = countUtility.countLogs(serviceConfig.getService1ServiceCountURI(), serviceConfig.getService1ServiceCountURIMethod(),serviceConfig.getService1ServiceCountMessage());
        String orderCount = countUtility.countLogs(serviceConfig.getService2ServiceCountURI(), serviceConfig.getService2ServiceCountURIMethod(),serviceConfig.getService2ServiceCountMessage());
        String productCount = countUtility.countLogs(serviceConfig.getService3ServiceCountURI(), serviceConfig.getService3ServiceCountURIMethod(),serviceConfig.getService3ServiceCountMessage());

        Dashboard dashboard = new Dashboard();
        List<Dashboard.Statistic> statisticList = new ArrayList<>();

        statisticList.add(new Dashboard.Statistic(serviceConfig.getService1ServiceName(), userCount));
        statisticList.add(new Dashboard.Statistic(serviceConfig.getService2ServiceName(), orderCount));
        statisticList.add(new Dashboard.Statistic(serviceConfig.getService3ServiceName(), productCount));

        List<Dashboard.HealthStatistics> healthStatisticsList = new ArrayList<>();

        // Handle service1 health
        try {
            String service1Health = restTemplate.getForObject(serviceConfig.getService1HealthUrl(), String.class);
            healthStatisticsList.add(new Dashboard.HealthStatistics(serviceConfig.getService1ServiceName(), service1Health));
        } catch (Exception e) {
            healthStatisticsList.add(new Dashboard.HealthStatistics(serviceConfig.getService1ServiceName(), "{\"status\":\"DOWN\"}"));
        }

        // Handle service2 health
        try {
            String service2Health = restTemplate.getForObject(serviceConfig.getService2HealthUrl(), String.class);
            healthStatisticsList.add(new Dashboard.HealthStatistics(serviceConfig.getService2ServiceName(), service2Health));
        } catch (Exception e) {
            healthStatisticsList.add(new Dashboard.HealthStatistics(serviceConfig.getService2ServiceName(), "{\\\"status\\\":\\\"DOWN\\\"}"));
        }

        // Handle service3 health
        try {
            String service3Health = restTemplate.getForObject(serviceConfig.getService3HealthUrl(), String.class);
            healthStatisticsList.add(new Dashboard.HealthStatistics(serviceConfig.getService3ServiceName(), service3Health));
        } catch (Exception e) {
            healthStatisticsList.add(new Dashboard.HealthStatistics(serviceConfig.getService3ServiceName(), "{\\\"status\\\":\\\"DOWN\\\"}"));
        }

        dashboard.setStatistics(statisticList);
        dashboard.setHealthStatistics(healthStatisticsList);
        return dashboard;
    }
}

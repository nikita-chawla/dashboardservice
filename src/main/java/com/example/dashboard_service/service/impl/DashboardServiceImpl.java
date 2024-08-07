package com.example.dashboard_service.service.impl;

import com.example.dashboard_service.config.ServiceConfig;
import com.example.dashboard_service.model.Dashboard;
import com.example.dashboard_service.service.DashboardService;
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
    public DashboardServiceImpl(RestTemplate restTemplate, ServiceConfig serviceConfig) {
        this.restTemplate = restTemplate;
        this.serviceConfig = serviceConfig;
    }

    @Override
    public Dashboard getDashboardDetails() {
        Integer userCount = restTemplate.getForObject(serviceConfig.getService1ServiceUrl(), Integer.class);
        Integer productCount = restTemplate.getForObject(serviceConfig.getService2ServiceUrl(), Integer.class);
        Integer orderCount = restTemplate.getForObject(serviceConfig.getService3ServiceUrl(), Integer.class);

        Dashboard dashboard = new Dashboard();
        List<Dashboard.Statistic> statisticList = new ArrayList<>();

        statisticList.add(new Dashboard.Statistic(serviceConfig.getService3ServiceName(), orderCount));
        statisticList.add(new Dashboard.Statistic(serviceConfig.getService2ServiceName(), productCount));
        statisticList.add(new Dashboard.Statistic(serviceConfig.getService1ServiceName(), userCount));

        List<Dashboard.HealthStatistics> healthStatisticsList = new ArrayList<>();

        healthStatisticsList.add(new Dashboard.HealthStatistics(serviceConfig.getService3ServiceName(), restTemplate.getForObject(serviceConfig.getService1HealthUrl(), String.class)));
        healthStatisticsList.add(new Dashboard.HealthStatistics(serviceConfig.getService2ServiceName(), restTemplate.getForObject(serviceConfig.getService2HealthUrl(), String.class)));
        healthStatisticsList.add(new Dashboard.HealthStatistics(serviceConfig.getService1ServiceName(), restTemplate.getForObject(serviceConfig.getService3HealthUrl(), String.class)));

        dashboard.setStatistics(statisticList);
        dashboard.setHealthStatistics(healthStatisticsList);
        return dashboard;
    }

//    public Map<String, String> getServicesHealth() {
//        Map<String, String> healthStatuses = new HashMap<>();
//        RestTemplate restTemplate = new RestTemplate();
//
//        for (String serviceName : serviceNames) {
//            String healthUrl = serviceHealthUrls.get(serviceName);
//            try {
//                ResponseEntity<String> response = restTemplate.getForEntity(healthUrl, String.class);
//                healthStatuses.put(serviceName, response.getBody());
//            } catch (HttpClientErrorException e) {
//                healthStatuses.put(serviceName, "DOWN");
//            }
//        }
//
//        return healthStatuses;
//    }
}

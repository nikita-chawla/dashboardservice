package com.example.dashboard_service.controller;

//import com.example.elasticSearch.model.Dashboard;
//import com.example.elasticSearch.service.DashboardService;
import com.example.dashboard_service.model.Dashboard;
import com.example.dashboard_service.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/details")
    public Dashboard getDashboardDetails() {
        return dashboardService.getDashboardDetails();
    }

    @Value("${application.services.service1.url}")
    private String service1ServiceUrl;

    @GetMapping("/service1-service-url")
    public String getService1ServiceUrl() {
        return service1ServiceUrl;
    }
}

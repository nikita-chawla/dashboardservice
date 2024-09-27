package com.example.dashboard.controller;

//import com.example.elasticSearch.model.Dashboard;
//import com.example.elasticSearch.service.DashboardService;
import com.example.dashboard.model.Dashboard;
import com.example.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
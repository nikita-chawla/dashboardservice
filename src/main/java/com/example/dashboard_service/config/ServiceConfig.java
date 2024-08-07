package com.example.dashboard_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Value("${application.services.service1.url}")
    private String service1ServiceUrl;

    @Value("${application.services.service2.url}")
    private String service2ServiceUrl;

    @Value("${application.services.service3.url}")
    private String service3ServiceUrl;

    @Value("${application.services.service1.healthurl}")
    private String service1HealthUrl;

    @Value("${application.services.service2.healthurl}")
    private String service2HealthUrl;

    @Value("${application.services.service3.healthurl}")
    private String service3HealthUrl;

    @Value("${application.services.service1.name}")
    private String service1ServiceName;

    @Value("${application.services.service2.name}")
    private String service2ServiceName;

    @Value("${application.services.service3.name}")
    private String service3ServiceName;

    public String getService1HealthUrl() {
        return service1HealthUrl;
    }

    public void setService1HealthUrl(String service1HealthUrl) {
        this.service1HealthUrl = service1HealthUrl;
    }

    public String getService2HealthUrl() {
        return service2HealthUrl;
    }

    public void setService2HealthUrl(String service2HealthUrl) {
        this.service2HealthUrl = service2HealthUrl;
    }

    public String getService3HealthUrl() {
        return service3HealthUrl;
    }

    public void setService3HealthUrl(String service3HealthUrl) {
        this.service3HealthUrl = service3HealthUrl;
    }

    public String getService1ServiceUrl() {
        return service1ServiceUrl;
    }

    public String getService2ServiceUrl() {
        return service2ServiceUrl;
    }

    public String getService3ServiceUrl() {
        return service3ServiceUrl;
    }

    public String getService1ServiceName() {
        return service1ServiceName;
    }

    public String getService2ServiceName() {
        return service2ServiceName;
    }

    public String getService3ServiceName() {
        return service3ServiceName;
    }
}

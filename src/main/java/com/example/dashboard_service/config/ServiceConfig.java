package com.example.dashboard_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "application")
public class ServiceConfig {

    @Value("${application.services.service1.counturi}")
    private String service1ServiceCountURI;

    @Value("${application.services.service2.counturi}")
    private String service2ServiceCountURI;

    @Value("${application.services.service3.counturi}")
    private String service3ServiceCountURI;

    @Value("${application.services.service1.counturimethod}")
    private String service1ServiceCountURIMethod;

    @Value("${application.services.service2.counturimethod}")
    private String service2ServiceCountURIMethod;

    @Value("${application.services.service3.counturimethod}")
    private String service3ServiceCountURIMethod;

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


    private List<Question> questions;

    // Getters and Setters for Services

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

    public String getService1ServiceCountURI() {
        return service1ServiceCountURI;
    }

    public String getService2ServiceCountURI() {
        return service2ServiceCountURI;
    }

    public String getService3ServiceCountURI() {
        return service3ServiceCountURI;
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

    public String getService1ServiceCountURIMethod() {
        return service1ServiceCountURIMethod;
    }

    public void setService1ServiceCountURIMethod(String service1ServiceCountURIMethod) {
        this.service1ServiceCountURIMethod = service1ServiceCountURIMethod;
    }

    public String getService2ServiceCountURIMethod() {
        return service2ServiceCountURIMethod;
    }

    public void setService2ServiceCountURIMethod(String service2ServiceCountURIMethod) {
        this.service2ServiceCountURIMethod = service2ServiceCountURIMethod;
    }

    public String getService3ServiceCountURIMethod() {
        return service3ServiceCountURIMethod;
    }

    public void setService3ServiceCountURIMethod(String service3ServiceCountURIMethod) {
        this.service3ServiceCountURIMethod = service3ServiceCountURIMethod;
    }

    // Getters and Setters for Questions

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // Inner class to represent Question
    public static class Question {
        private int id;
        private String question;
        private String query;

        // Getters and Setters

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }
}

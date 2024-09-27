package com.example.dashboard.model;

import java.util.List;

public class Dashboard {
    private List<Statistic> statistics;
    private Geographicals geographicals;
    private List<HealthStatistics> healthStatistics;

    // Getters and Setters

    public List<Statistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistic> statistics) {
        this.statistics = statistics;
    }

    public List<HealthStatistics> getHealthStatistics() {
        return healthStatistics;
    }

    public void setHealthStatistics(List<HealthStatistics> healthStatistics) {
        this.healthStatistics = healthStatistics;
    }

    public Geographicals getGeographicals() {
        return geographicals;
    }

    public void setGeographicals(Geographicals geographicals) {
        this.geographicals = geographicals;
    }

    // Inner classes

    public static class Statistic {
        private String name;
        private String count;

        public Statistic() {}

        public Statistic(String name, String count) {
            this.name = name;
            this.count = count;
        }

        // Getters and Setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

    public static class HealthStatistics {
        private String name;
        private String health;

        public HealthStatistics() {}

        public HealthStatistics(String name, String health) {
            this.name = name;
            this.health = health;
        }

        // Getters and Setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHealth() {
            return health;
        }

        public void setCount(String health) {
            this.health = health;
        }
    }

    public static class Geographicals {
        // Define properties and methods for Geographicals as needed

        // Example:
        // private String region;
        // private String data;

        // Getters and Setters
        // public String getRegion() {
        //     return region;
        // }
        //
        // public void setRegion(String region) {
        //     this.region = region;
        // }
        //
        // public String getData() {
        //     return data;
        // }
        //
        // public void setData(String data) {
        //     this.data = data;
        // }
    }

    public static class Health {
        private String health;

        // Getters and Setters

        public String getNikitaStatus() {
            return health;
        }

        public void setNikitaStatus(String nikitaStatus) {
            this.health = nikitaStatus;
        }
    }
}

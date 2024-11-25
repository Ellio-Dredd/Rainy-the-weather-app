package com.example.rainy;

public class RVModel {
    private String temperature;
    private String windSpeed;
    private String time;
    private String conditionIcon;// URL of weather condition icon

    public RVModel(String temperature, String windSpeed, String time, String conditionIcon) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.time = time;
        this.conditionIcon = conditionIcon;
    }
    // Getter methods for each field
    public String getTemperature() {
        return temperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getTime() {
        return time;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }
}


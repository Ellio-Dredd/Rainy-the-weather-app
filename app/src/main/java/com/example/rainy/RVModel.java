package com.example.rainy;

public class RVModel {
    private String temperature;
    private String windSpeed;
    private String time;
    private String conditionIcon;// URL of weather condition icon

    private String temp_min;
    private String temp_max;

    public RVModel(String temperature, String windSpeed, String time, String conditionIcon, String max, String min) {

        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.time = time;
        this.conditionIcon = conditionIcon;

        this.temp_min = min;
        this.temp_max = max;


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

    public String getTemp_min() {return temp_min;}

    public String getTemp_max() {return temp_max;}


}


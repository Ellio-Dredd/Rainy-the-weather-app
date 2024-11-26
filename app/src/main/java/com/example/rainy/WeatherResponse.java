package com.example.rainy;

import java.util.List;

public class WeatherResponse {
    private List<HourlyWeather> list; // Represents the hourly forecasts

    public List<HourlyWeather> getHourly() {
        return list;
    }
}

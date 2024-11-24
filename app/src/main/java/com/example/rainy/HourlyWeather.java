package com.example.rainy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HourlyWeather {
    private long dt; // UNIX timestamp
    private Main main; // Nested object for temperature
    private List<Weather> weather; // Nested object for description

    public String getFormattedTime() {
        Date date = new Date(dt * 1000L); // Convert to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(date);
    }

    public double getTemperature() {
        return main.temp;
    }

    public String getDescription() {
        return weather.get(0).description;
    }

    // Inner classes for nested JSON objects
    public static class Main {
        double temp;
    }

    public static class Weather {
        String description;
    }
}

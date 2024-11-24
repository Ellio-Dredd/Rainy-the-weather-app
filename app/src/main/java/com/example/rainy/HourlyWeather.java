package com.example.rainy;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HourlyWeather {
    private long dt; // UNIX timestamp
    private Main main; // Nested object for temperature
    private List<Weather> weather;// Nested object for description
    private String dt_txt;
    String dat_txt;

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

    public String getcaldate(){
        //return  dt_txt;


        dat_txt = dt_txt;

        dat_txt=dt_txt.substring(5,10);

//        int day=Integer.parseInt(dt_txt.substring(8,10));
//        int month=Integer.parseInt(dt_txt.substring(5,7));
//
//        int year = LocalDate.now().getYear(); //to get the current year

        String Sday=dt_txt.substring(8,10);
        String Smonth=dt_txt.substring(5,7);

//        // Create a LocalDate object
//        LocalDate date = LocalDate.of(year, month, day);
//
//        // Get the day name
//        String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

        dat_txt= Smonth+"/"+Sday  ;



       // return dat_txt;

        return dat_txt;
    }

    public String dayName(){
        int day=Integer.parseInt(dt_txt.substring(8,10));
        int month=Integer.parseInt(dt_txt.substring(5,7));

        int year = LocalDate.now().getYear(); //to get the current year

        // Create a LocalDate object
        LocalDate date = LocalDate.of(year, month, day);

        // Get the day name
        String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

        return dayName;
    }
}

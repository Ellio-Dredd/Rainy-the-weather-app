package com.example.rainy;

public class dayForcastModel {

    String DayName;
    String DayForcast;


    public dayForcastModel(String dayForcast, String dayName) {
        this.DayForcast = dayForcast;
        this.DayName = dayName;
    }

    public String getDayForcast() {
        return DayForcast;
    }

    public String getDayName() {
        return DayName;
    }
}

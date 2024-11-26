package com.example.rainy;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("data/2.5/forecast") // Endpoint for 5-day/3-hour forecast
    Call<WeatherResponse> getHourlyForecast(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}

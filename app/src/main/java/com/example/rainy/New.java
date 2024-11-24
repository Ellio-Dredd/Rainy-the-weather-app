package com.example.rainy;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class New extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    private List<HourlyWeather> weatherList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        weatherAdapter = new WeatherAdapter(weatherList);
        recyclerView.setAdapter(weatherAdapter);

        fetchWeatherData();
    }

    private void fetchWeatherData() {
        WeatherApiService apiService = RetrofitClient.getInstance().create(WeatherApiService.class);
        Call<WeatherResponse> call = apiService.getHourlyForecast(
                37.7749, -122.4194, "2e97a68426a7854ceac53832f30380d1", "metric"
        );

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherList.clear();
                    for (HourlyWeather weather : response.body().getHourly()) {
                        weatherList.add(weather);
                    }
                    weatherAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    //date -----
    public void Datecal(){

    }
}


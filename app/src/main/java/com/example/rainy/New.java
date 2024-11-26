package com.example.rainy;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
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

    double latitu ;
    double longitu ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        weatherAdapter = new WeatherAdapter(weatherList);
        recyclerView.setAdapter(weatherAdapter);

        // Retrieve the data passed from MainActivity
        Intent intent = getIntent();
         latitu = intent.getDoubleExtra("lat",0);
         longitu = intent.getDoubleExtra("longe",0);



        fetchWeatherData();
    }
    Intent intent = getIntent();

    private void fetchWeatherData() {


        WeatherApiService apiService = RetrofitClient.getInstance().create(WeatherApiService.class);
        Call<WeatherResponse> call = apiService.getHourlyForecast(
                latitu, longitu, "2e97a68426a7854ceac53832f30380d1", "metric"
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


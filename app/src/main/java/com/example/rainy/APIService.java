package com.example.rainy;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APIService {
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private static final String API_KEY = "2e97a68426a7854ceac53832f30380d1"; // Replace with your OpenWeather API key
    private Context context;
    private RequestQueue requestQueue;

    public APIService(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchWeatherData(String cityName, WeatherDataCallback callback) {
        String url = API_URL + "?q=" + cityName + "&units=metric&appid=" + API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<com.example.rainy.RVModel> weatherList = parseWeatherData(response);
                            callback.onSuccess(weatherList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError("Error parsing weather data.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("APIService", "Error: " + error.getMessage());
                        callback.onError("Failed to fetch weather data.");
                    }
                }
        );

        requestQueue.add(request);
    }

    private ArrayList<com.example.rainy.RVModel> parseWeatherData(JSONObject response) throws JSONException {
        ArrayList<com.example.rainy.RVModel> weatherList = new ArrayList<>();
        JSONArray list = response.getJSONArray("list");

        for (int i = 0; i < list.length(); i++) {
            JSONObject item = list.getJSONObject(i);
            String time = item.getString("dt_txt");
            String temperature = item.getJSONObject("main").getString("temp");
            String windSpeed = item.getJSONObject("wind").getString("speed");
            String icon = item.getJSONArray("weather").getJSONObject(0).getString("icon");

            com.example.rainy.RVModel model = new com.example.rainy.RVModel(temperature,windSpeed,time,icon);
            weatherList.add(model);
        }

        return weatherList;
    }

    public interface WeatherDataCallback {
        void onSuccess(ArrayList<com.example.rainy.RVModel> weatherList);
        void onError(String errorMessage);
    }
}

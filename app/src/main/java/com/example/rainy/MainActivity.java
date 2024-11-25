package com.example.rainy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout Home;
    private ProgressBar Loading;
    private TextView CityName, Temperature, Condition;
    private RecyclerView WeatherRV;
    private ImageView background, icon, search;
    private ArrayList<RVModel> RVModelArrayList;
    private com.example.rainy.Adapter adapter;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private EditText CityEdit;// EditText for city input

    double lati ;
    double longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkPermissions();

        Home = findViewById(R.id.Home);
        Loading = findViewById(R.id.loading);
        CityName = findViewById(R.id.city_name_title);
        Temperature = findViewById(R.id.Temp);
        Condition = findViewById(R.id.Condition);
        icon = findViewById(R.id.Temp_Condition);
        WeatherRV = findViewById(R.id.Rv_weather);
        background = findViewById(R.id.background);
        search = findViewById(R.id.search_logo);
        CityEdit = findViewById(R.id.input_city);// Initialize EditText for city input


        RVModelArrayList = new ArrayList<>();
        adapter = new com.example.rainy.Adapter(this, RVModelArrayList);
        WeatherRV.setAdapter(adapter);

        search.setOnClickListener(v -> {
            String city = CityEdit.getText().toString();
            if (city.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
            } else {
                getWeatherInfo(city);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        // Find the button by its ID
        Button button = findViewById(R.id.btn_hourly_forecast);

        // Set OnClickListener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SecondActivity
                Intent intent = new Intent(MainActivity.this, New.class);

                // Optionally pass data to the second activity
                intent.putExtra("lat", lati);
                intent.putExtra("longe", longi);

                // Start SecondActivity
                startActivity(intent);
            }
        });

    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLastLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Location location = task.getResult();
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String city = getCityName(latitude, longitude);
                        if (!city.equals("Not Found")) {
                            getWeatherInfo(city);
                        }
                    } else {
                        Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getCityName(double latitude, double longitude) {
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
            for (Address adr : addresses) {
                if (adr != null) {
                    String city = adr.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                    } else {
                        Log.d("TAG", "CITY NOT FOUND");
                        Toast.makeText(this, "User City Not Found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    private void getWeatherInfo(String city) {
        // Build geocode URL
        String geocodeUrl = "https://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=42048c8d7291c198db7dbfe1b4f437ff";

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

// Change JsonObjectRequest to JsonArrayRequest to handle the array response
        JsonArrayRequest geocodeRequest = new JsonArrayRequest(Request.Method.GET, geocodeUrl, null,
                response -> {
                    try {
                        if (response != null && response.length() > 0) {
                            // Get the first result from the response array
                            JSONObject firstResult = response.getJSONObject(0);

                            // Extract latitude and longitude from the first result
                            double latitude = firstResult.getDouble("lat");
                            double longitude = firstResult.getDouble("lon");

                            lati=latitude;
                            longi=longitude;


                            // Use the coordinates to fetch weather data
                            getWeatherDataUsingCoordinates(latitude, longitude);
                        } else {
                            Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error parsing geocoding data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error with Geocoding API", Toast.LENGTH_SHORT).show();
                });

// Add the request to the Volley queue
        requestQueue.add(geocodeRequest);

    }



    private void getWeatherDataUsingCoordinates(double latitude, double longitude) {



        // Fixing the weather URL
        String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=2e97a68426a7854ceac53832f30380d1&units=metric";

        // Ensure RVModelArrayList is initialized
        if (RVModelArrayList == null) {
            RVModelArrayList = new ArrayList<>();
        }

        // Create a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // Create a JsonObjectRequest to fetch weather data
        JsonObjectRequest weatherRequest = new JsonObjectRequest(Request.Method.GET, weatherUrl, null,
                response -> {
                    try {
                        // Parse the response JSON
                        String temperature = response.getJSONObject("main").getString("temp"); // Get temperature
                        String windSpeed = response.getJSONObject("wind").getString("speed"); // Get wind speed
                        String time = response.getString("dt"); // Time of the weather data (UNIX timestamp)
                        String conditionIcon = response.getJSONArray("weather").getJSONObject(0).getString("icon"); // Get weather icon code

                        // Convert time from UNIX timestamp to human-readable format
                        long timeInMillis = Long.parseLong(time) * 1000; // Convert UNIX timestamp to milliseconds
                        Date date = new Date(timeInMillis);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        String formattedTime = sdf.format(date);

                        // Populate the RecyclerView model list
                        RVModelArrayList.clear();  // Clear any existing data
                        RVModelArrayList.add(new RVModel(temperature, windSpeed, formattedTime, conditionIcon));  // Add new data to ArrayList
                        adapter.notifyDataSetChanged();  // Notify adapter that the data has changed

                        // Set data to UI components
                        Temperature.setText(temperature + "°C");
                        Condition.setText(response.getJSONArray("weather").getJSONObject(0).getString("description"));
                        Picasso.get().load("https://openweathermap.org/img/wn/" + conditionIcon + "@2x.png").into(icon);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error parsing weather data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle network or request error
                    error.printStackTrace();
                    Toast.makeText(MainActivity.this, "Unable to fetch weather data. Please check your connection.", Toast.LENGTH_SHORT).show();
                });

        // Add the request to the request queue
        requestQueue.add(weatherRequest);
    }





}

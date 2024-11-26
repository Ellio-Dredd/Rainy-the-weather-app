package com.example.rainy;

import android.os.Bundle;

<<<<<<< Updated upstream
import androidx.activity.EdgeToEdge;
=======
import androidx.annotation.NonNull;
>>>>>>> Stashed changes
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
<<<<<<< Updated upstream
=======
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
>>>>>>> Stashed changes

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
<<<<<<< Updated upstream
}
=======

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
                        String conditionIcon = response.getJSONArray("weather").getJSONObject(0).getString("icon");// Get weather icon code
                        String min = response.getJSONObject("main").getString("temp_min");
                        String max = response.getJSONObject("main").getString("temp_max");

                        // Convert time from UNIX timestamp to human-readable format
                        long timeInMillis = Long.parseLong(time) * 1000; // Convert UNIX timestamp to milliseconds
                        Date date = new Date(timeInMillis);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        String formattedTime = sdf.format(date);

                        // Populate the RecyclerView model list
                        RVModelArrayList.clear();  // Clear any existing data
                        RVModelArrayList.add(new RVModel(temperature, windSpeed, formattedTime, conditionIcon, min, max));  // Add new data to ArrayList
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
>>>>>>> Stashed changes

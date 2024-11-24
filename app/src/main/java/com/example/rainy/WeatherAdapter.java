package com.example.rainy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<HourlyWeather> hourlyWeatherList;

    public WeatherAdapter(List<HourlyWeather> hourlyWeatherList) {
        this.hourlyWeatherList = hourlyWeatherList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        HourlyWeather weather = hourlyWeatherList.get(position);
        holder.timeTextView.setText(weather.getFormattedTime());
        holder.tempTextView.setText(String.format("%s°C", weather.getTemperature()));
        holder.descriptionTextView.setText(weather.getDescription());
        holder.dateView.setText(weather.getcaldate());
        holder.dayNameView.setText(weather.dayName());

    }

    @Override
    public int getItemCount() {
        return hourlyWeatherList.size();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView, tempTextView, descriptionTextView,dateView,dayNameView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            tempTextView = itemView.findViewById(R.id.tempTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateView =itemView.findViewById(R.id.dateView);
            dayNameView =itemView.findViewById(R.id.dayNameView);
        }
    }
}

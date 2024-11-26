package com.example.rainy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private ArrayList<RVModel> RVModelArrayList;

    public Adapter(Context context, ArrayList<RVModel> RVModelArrayList) {
        this.context = context;
        this.RVModelArrayList = RVModelArrayList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_rv, parent, false);
        return new ViewHolder(view);  // Inflate and return ViewHolder
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (RVModelArrayList != null && RVModelArrayList.get(position) != null) {
            RVModel model = RVModelArrayList.get(position);

            // Safely handle null string values using conditional checks



            String temperature_min = model.getTemp_min();
            if (temperature_min != null) {
                holder.min.setText("Min:"+temperature_min + "°C");
            } else {
                holder.min.setText("No Data");
            }

            String temperature_max = model.getTemp_max();
            if (temperature_max != null) {
                holder.max.setText("Max:"+temperature_max + "°C");
            } else {
                holder.max.setText("No Data");

            }

            String windSpeed = model.getWindSpeed();
            if (windSpeed != null) {
                holder.wind.setText("Wind Speed: " + windSpeed + " km/h");
            } else {
                holder.wind.setText("Wind Speed: N/A");
            }

            String time = model.getTime();
            if (time != null) {
                holder.time.setText(time);
            } else {
                holder.time.setText("Time: N/A");
            }


        } else {
            // Set default values if the data model is null
            holder.wind.setText("Wind Speed: N/A");
            holder.time.setText("Time: N/A");
            holder.min.setText("Temperature Min: N/A");
            holder.max.setText("Temperature Max: N/A");

        }
    }

    @Override
    public int getItemCount() {
        return RVModelArrayList != null ? RVModelArrayList.size() : 0;  // Safely return item count
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView wind, time, min, max;


        // Constructor to initialize Views
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wind = itemView.findViewById(R.id.windSpeed_rv);

            time = itemView.findViewById(R.id.time_rv);
            min = itemView.findViewById(R.id.temp_min_rv);
            max = itemView.findViewById(R.id.temp_max_rv);

        }
    }
}

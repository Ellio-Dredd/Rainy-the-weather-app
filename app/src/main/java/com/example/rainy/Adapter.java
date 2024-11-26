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
            String temperature = model.getTemperature();
            if (temperature != null) {
                holder.temperature.setText(temperature + "°C");
            } else {
                holder.temperature.setText("No Data");
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

            // For condition icon, use Picasso to load the weather icon
            String conditionIcon = model.getConditionIcon();
            if (conditionIcon != null) {
                Picasso.get().load("https://openweathermap.org/img/wn/" + conditionIcon + "@2x.png").into(holder.condition);
            } else {
                holder.condition.setImageResource(R.drawable.default_icon); // Use a default icon if null
            }
        } else {
            // Set default values if the data model is null
            holder.temperature.setText("No Data Available");
            holder.wind.setText("Wind Speed: N/A");
            holder.time.setText("Time: N/A");
            holder.condition.setImageResource(R.drawable.default_icon); // Default icon
        }
    }

    @Override
    public int getItemCount() {
        return RVModelArrayList != null ? RVModelArrayList.size() : 0;  // Safely return item count
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wind, temperature, time;
        private ImageView condition;

        // Constructor to initialize Views
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wind = itemView.findViewById(R.id.windSpeed_rv);
            temperature = itemView.findViewById(R.id.temperature_rv);
            time = itemView.findViewById(R.id.time_rv);
            condition = itemView.findViewById(R.id.condition_rv);
        }
    }
}

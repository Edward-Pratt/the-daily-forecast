package com.edwardpratt.thedailyforecast.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edwardpratt.thedailyforecast.R;
import com.edwardpratt.thedailyforecast.model.WeatherCurrent;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private final List<WeatherCurrent> weatherList;
    private final Context context;

    public WeatherAdapter(Context context, List<WeatherCurrent> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherCurrent weather = weatherList.get(position);
        holder.time.setText(weather.getTimeString());
        holder.date.setText(weather.getDateString());
        holder.temperature.setText(String.valueOf(weather.getTemperature()));
        holder.windSpeed.setText(String.valueOf(weather.getWindSpeed()));
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView date;
        public TextView temperature;
        public TextView windSpeed;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            //time = itemView.findViewById(R.id.weatherDate);
            date = itemView.findViewById(R.id.weatherDate);
            temperature = itemView.findViewById(R.id.weatherTemp);
            //windSpeed = itemView.findViewById(R.id.weatherWindSpeed);
        }
    }

}


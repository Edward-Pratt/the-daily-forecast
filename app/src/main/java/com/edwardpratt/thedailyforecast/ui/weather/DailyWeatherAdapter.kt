package com.edwardpratt.thedailyforecast.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edwardpratt.thedailyforecast.R
import com.edwardpratt.thedailyforecast.model.DailyWeather

class DailyWeatherAdapter(private val dailyWeather: DailyWeather) :
    RecyclerView.Adapter<DailyWeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.tvDate)
        val maxTemp: TextView = view.findViewById(R.id.tvMaxTemp)
        val minTemp: TextView = view.findViewById(R.id.tvMinTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_forecast, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.date.text = dailyWeather.time[position]
        holder.maxTemp.text = "Max: ${dailyWeather.temperature_2m_max[position]}°C"
        holder.minTemp.text = "Min: ${dailyWeather.temperature_2m_min[position]}°C"
    }

    override fun getItemCount() = dailyWeather.time.size
}

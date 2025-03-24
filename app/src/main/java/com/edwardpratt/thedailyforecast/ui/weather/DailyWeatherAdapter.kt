package com.edwardpratt.thedailyforecast.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edwardpratt.thedailyforecast.R
import com.edwardpratt.thedailyforecast.model.DailyWeather
import com.edwardpratt.thedailyforecast.utils.codeToSymbol

class DailyWeatherAdapter(private val dailyWeather: DailyWeather) :
    RecyclerView.Adapter<DailyWeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.tvDate)
        val maxTemp: TextView = view.findViewById(R.id.tvMaxTemp)
        val minTemp: TextView = view.findViewById(R.id.tvMinTemp)
        val weatherIcon: ImageView = view.findViewById(R.id.weatherIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_forecast, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        if (dailyWeather == null || dailyWeather.time?.isEmpty() == true) return

        holder.date.text = dailyWeather.time?.getOrNull(position) ?: "Unknown"
        holder.maxTemp.text = "Max: ${dailyWeather.temperature_2m_max?.getOrNull(position) ?: "0"}°C"
        holder.minTemp.text = "Min: ${dailyWeather.temperature_2m_min?.getOrNull(position) ?: "0"}°C"

        val weatherCode = dailyWeather.weather_code?.getOrNull(position) ?: 0
        val weatherIcon = codeToSymbol.getWeatherIcon(weatherCode)
        holder.weatherIcon.setImageResource(weatherIcon)
    }

    override fun getItemCount(): Int = dailyWeather.time?.size ?: 0
}

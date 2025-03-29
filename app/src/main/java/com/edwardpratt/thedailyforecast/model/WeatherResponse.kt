package com.edwardpratt.thedailyforecast.model

import com.google.gson.annotations.SerializedName


// Represents the response from the weather API
data class WeatherResponse(
    @SerializedName("current_weather") val current_weather: CurrentWeather,
    @SerializedName("daily") val daily: DailyWeather
)

data class CurrentWeather(
    @SerializedName("temperature") val temperature: Double?,
    @SerializedName("weather_code") val weather_code: Int?,
    @SerializedName("winddirection") val windDirection: Int?,
    @SerializedName("windspeed") val windSpeed: Double,

)

data class DailyWeather(
    @SerializedName("time") val time: List<String>?,
    @SerializedName("weather_code") val weather_code: List<Int>?,
    @SerializedName("temperature_2m_max") val temperature_2m_max: List<Double>?,
    @SerializedName("temperature_2m_min") val temperature_2m_min: List<Double>?
)
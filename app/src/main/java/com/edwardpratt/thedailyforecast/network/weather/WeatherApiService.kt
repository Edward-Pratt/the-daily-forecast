package com.edwardpratt.thedailyforecast.network.weather

import com.edwardpratt.thedailyforecast.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,precipitation_sum",  // Example valid daily query
        @Query("timezone") timezone: String = "auto"
    ): Response<WeatherResponse>
}

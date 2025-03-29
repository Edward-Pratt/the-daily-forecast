package com.edwardpratt.thedailyforecast.utils

import com.edwardpratt.thedailyforecast.R

// This object maps weather codes to drawable resource IDs for weather icons.
object codeToSymbol {
    fun getWeatherIcon(weatherCode: Int?): Int {
        return when (weatherCode) {
            0 -> R.drawable.ic_sunny         // Clear sky
            1, 2, 3 -> R.drawable.ic_cloudy   // Partly cloudy
            45, 48 -> R.drawable.ic_foggy     // Fog
            in 51..55 -> R.drawable.ic_drizzle // Drizzle
            in 61..65 -> R.drawable.ic_rainy   // Rain
            in 71..75 -> R.drawable.ic_snow    // Snow
            in 95..99 -> R.drawable.ic_storm   // Thunderstorm
            else -> R.drawable.ic_sunny    // Default icon

        }
    }
}
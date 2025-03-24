package com.edwardpratt.thedailyforecast.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwardpratt.thedailyforecast.model.WeatherResponse
import com.edwardpratt.thedailyforecast.network.WeatherApiClient
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> get() = _weatherData

    private val _locationName = MutableLiveData<String>()
    val locationName: LiveData<String> get() = _locationName

    fun fetchWeather(latitude: Double, longitude: Double, cityName: String?) {
        _locationName.value = cityName ?: "Unknown Location"

        viewModelScope.launch {
            try {
                val response: Response<WeatherResponse> = WeatherApiClient.instance.getWeather(
                    latitude,
                    longitude,
                    currentWeather = true,
                    daily = "temperature_2m_max,temperature_2m_min,weather_code",
                    timezone = "auto"
                )

                if (response.isSuccessful) {
                    _weatherData.value = response.body()
                } else {
                    _weatherData.value = null
                }
            } catch (e: Exception) {
                _weatherData.value = null
            }
        }
    }
}

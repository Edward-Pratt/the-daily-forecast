package com.edwardpratt.thedailyforecast.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edwardpratt.thedailyforecast.model.WeatherResponse
import com.edwardpratt.thedailyforecast.network.WeatherApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> get() = _weatherData

    fun fetchWeather(latitude: Double, longitude: Double) {
        val call = WeatherApiClient.instance.getWeather(latitude, longitude)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    _weatherData.value = response.body()
                } else {
                    _weatherData.value = null
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _weatherData.value = null
            }
        })
    }
}

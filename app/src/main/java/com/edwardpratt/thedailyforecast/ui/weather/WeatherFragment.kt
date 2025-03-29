package com.edwardpratt.thedailyforecast.ui.weather

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edwardpratt.thedailyforecast.databinding.FragmentWeatherBinding
import com.edwardpratt.thedailyforecast.utils.LocationHelper
import com.edwardpratt.thedailyforecast.utils.codeToSymbol

class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherViewModel
    private lateinit var dailyWeatherAdapter: DailyWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        viewModel.fetchWeather(51.5074, -0.1278, "London")
        requestLocationAndFetchWeather()

        // Watches for location change
        viewModel.locationName.observe(viewLifecycleOwner, Observer { location ->
            binding.locationTextView.text = location
        })

        // Watches for weather data change
        viewModel.weatherData.observe(viewLifecycleOwner) { weatherResponse ->
            Log.d("WeatherDebug", "Weather response: $weatherResponse")

            weatherResponse?.daily?.let { daily ->
                Log.d("WeatherDebug", "Daily weather data received: ${daily.time}")
            } ?: Log.e("WeatherDebug", "Daily weather data is NULL!")

            weatherResponse?.let {
                binding.tvCurrentTemp.text = "${it.current_weather.temperature}°C"
                binding.tvWindSpeed.text = "Wind: ${it.current_weather.windSpeed} km/h"
                binding.tvWindDirection.text = "Dir: ${it.current_weather.windDirection}°"

                val weatherIcon = codeToSymbol.getWeatherIcon(it.current_weather.weather_code)
                binding.weatherIcon.setImageResource(weatherIcon)

                // Set up RecyclerView Adapter
                if (it.daily != null && it.daily.time?.isNotEmpty() == true) {
                    dailyWeatherAdapter = DailyWeatherAdapter(it.daily)
                    binding.recyclerView.adapter = dailyWeatherAdapter
                }
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            requestLocationAndFetchWeather()
        }



        return view
    }

    private fun requestLocationAndFetchWeather() {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        val locationHelper = LocationHelper(requireContext())
        locationHelper.getCurrentLocation { latitude, longitude, cityName ->
            if (latitude != null && longitude != null) {
                viewModel.fetchWeather(latitude, longitude, cityName)
            } else {
                // Fallback: Load weather for a default location (e.g., London)
                viewModel.fetchWeather(51.5074, -0.1278, "London")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}

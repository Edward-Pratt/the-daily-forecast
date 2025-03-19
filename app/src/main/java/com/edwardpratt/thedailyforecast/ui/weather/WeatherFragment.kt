package com.edwardpratt.thedailyforecast.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.edwardpratt.thedailyforecast.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherData.observe(viewLifecycleOwner) { weather ->
            weather?.let {
                binding.tempTextView.text = "${it.current_weather.temperature}°C"
                binding.windTextView.text = "Wind: ${it.current_weather.windspeed} km/h"
            } ?: run {
                binding.tempTextView.text = "Failed to load data"
            }
        }

        viewModel.fetchWeather(50.72, -3.53) // Example: Exeter, UK
    }
}

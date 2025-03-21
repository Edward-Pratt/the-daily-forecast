package com.edwardpratt.thedailyforecast.ui.weather

import android.content.pm.PackageManager
import android.os.Bundle
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

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        viewModel.locationName.observe(viewLifecycleOwner, Observer { location ->
            binding.locationTextView.text = location
        })

        viewModel.weatherData.observe(viewLifecycleOwner) { weatherResponse ->
            weatherResponse?.let {
                binding.tvCurrentTemp.text = "${it.current_weather.temperature}°C"

                // Set up RecyclerView Adapter
                dailyWeatherAdapter = DailyWeatherAdapter(it.daily)
                binding.recyclerView.adapter = dailyWeatherAdapter
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            requestLocationAndFetchWeather()
        }

        //viewModel.fetchWeather(50.72, -3.52, "Exeter") // Example coordinates

        requestLocationAndFetchWeather()

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
            viewModel.fetchWeather(latitude, longitude, cityName)
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

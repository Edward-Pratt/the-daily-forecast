package com.edwardpratt.thedailyforecast.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edwardpratt.thedailyforecast.R
import com.edwardpratt.thedailyforecast.databinding.FragmentHomeBinding
import com.edwardpratt.thedailyforecast.ui.NewsFragment
import com.edwardpratt.thedailyforecast.ui.finance.FinanceFragment
import com.edwardpratt.thedailyforecast.ui.weather.WeatherFragment
import com.edwardpratt.thedailyforecast.ui.settings.SettingsFragment

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)


        // Button click listeners to navigate to other fragments
        binding.btnWeather.setOnClickListener { openFragment(WeatherFragment()) }
        binding.btnFinance.setOnClickListener { openFragment(FinanceFragment()) }
        binding.btnNews.setOnClickListener { openFragment(NewsFragment()) }
        binding.btnSettings.setOnClickListener { openFragment(SettingsFragment()) }
    }

    // Function to replace the current fragment with a new one
    private fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}

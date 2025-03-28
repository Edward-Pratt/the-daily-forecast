package com.edwardpratt.thedailyforecast.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.edwardpratt.thedailyforecast.R
import com.edwardpratt.thedailyforecast.databinding.ActivityMainBinding
import com.edwardpratt.thedailyforecast.ui.finance.FinanceFragment
import com.edwardpratt.thedailyforecast.ui.home.HomeFragment
import com.edwardpratt.thedailyforecast.ui.weather.WeatherFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Toolbar
        setSupportActionBar(binding.toolbar)

        // Set up Navigation Drawer
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Handle Navigation Item Clicks
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_weather -> loadFragment(WeatherFragment())
                R.id.nav_news -> loadFragment(NewsFragment())
                R.id.nav_finance -> loadFragment(FinanceFragment())
                R.id.nav_home -> loadFragment(HomeFragment())
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Load Default Fragment if no saved state
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())  // Default to HomeFragment
            binding.navView.setCheckedItem(R.id.nav_home)
        }
    }

    // Helper function to load fragments
    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        // No need to set binding to null, ViewBinding handles this automatically.
    }
}

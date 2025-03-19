package com.edwardpratt.thedailyforecast.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.edwardpratt.thedailyforecast.databinding.ActivityMainBinding;
import com.edwardpratt.thedailyforecast.ui.weather.WeatherFragment;
import com.edwardpratt.thedailyforecast.R;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up Toolbar
        setSupportActionBar(binding.toolbar);

        // Set up Navigation Drawer
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.nav_open, R.string.nav_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Handle Navigation Item Clicks
        binding.navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_weather) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new WeatherFragment())
                        .commit();
            } else if (id == R.id.nav_news) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NewsFragment())
                        .commit();
            } else if (id == R.id.nav_finance) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FinanceFragment())
                        .commit();
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Load Default Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WeatherFragment())
                    .commit();
            binding.navView.setCheckedItem(R.id.nav_weather);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // Prevent memory leaks
    }
}

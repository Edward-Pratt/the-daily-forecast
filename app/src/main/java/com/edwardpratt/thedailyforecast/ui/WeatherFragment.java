package com.edwardpratt.thedailyforecast.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.VolleyError;
import com.edwardpratt.thedailyforecast.R;
import com.edwardpratt.thedailyforecast.model.WeatherCurrent;
import com.edwardpratt.thedailyforecast.network.WeatherApi;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {
    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    private List<WeatherCurrent> weatherCurrentList;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        recyclerView = view.findViewById(R.id.weatherRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        weatherCurrentList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(requireContext(), weatherCurrentList);
        recyclerView.setAdapter(weatherAdapter);

        fetchWeatherData();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchWeatherData();
            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    private void fetchWeatherData(){
        WeatherApi apiService = new WeatherApi(requireContext());

        apiService.fetchCurrentWeather(new WeatherApi.WeatherCurrentResponseListener(){
            @Override
            public void onResponse(List<WeatherCurrent> weather){
                weatherCurrentList.clear();
                weatherCurrentList.addAll(weather);
                weatherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error){
                Toast.makeText(getContext(), "Failed to fetch weather", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

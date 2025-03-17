package com.edwardpratt.thedailyforecast.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.edwardpratt.thedailyforecast.model.NewsArticle;
import com.edwardpratt.thedailyforecast.model.WeatherCurrent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherApi {


    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast?latitude=50.7371&longitude=-3.5315&hourly=temperature_2m,wind_speed_10m";

    private RequestQueue requestQueue;


    public WeatherApi(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void fetchCurrentWeather(final WeatherCurrentResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, BASE_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<WeatherCurrent> weatherCurrentList = parseJson(response);
                        listener.onResponse(weatherCurrentList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                });
    };



    private List<WeatherCurrent> parseJson(JSONObject response){
        List<WeatherCurrent> weatherCurrentList = new ArrayList<>();
        try { JSONArray weatherCurrentArray = response.getJSONArray("weatherCurrent");


            for (int i=0; i<weatherCurrentArray.length(); i++){
                JSONObject weatherCurrentObj = weatherCurrentArray.getJSONObject(i);

                String time = weatherCurrentObj.optString("time", "No Time");
                Double temperature = weatherCurrentObj.optDouble("temperature", 0.0);
                Double wind_speed = weatherCurrentObj.optDouble("wind_speed", 0.0);

                LocalDateTime time1 = LocalDateTime.parse(time);

                WeatherCurrent weatherCurrent = new WeatherCurrent(time1, temperature, wind_speed);
                weatherCurrentList.add(weatherCurrent);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return weatherCurrentList;


    }

    public interface  WeatherCurrentResponseListener {
        void onResponse(List<WeatherCurrent> response);

        void onError(VolleyError error);
    }

}

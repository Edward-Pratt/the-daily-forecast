package com.edwardpratt.thedailyforecast.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edwardpratt.thedailyforecast.model.NewsArticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsApi {
    private static final String API_KEY = "9e5b9279212759a2a3a9291156491313";
    private static final String BASE_URL = "https://gnews.io/api/v4/top-headlines?category=general&lang=en&country=gb&max=10&apikey=" + API_KEY;
    private RequestQueue requestQueue;

    public NewsApi(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void fetchNews(final NewsResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, BASE_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<NewsArticle> newsArticles = parseJson(response);
                        listener.onResponse(newsArticles);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                });

        requestQueue.add(request);
    }

    private List<NewsArticle> parseJson(JSONObject response) {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            JSONArray articlesArray = response.getJSONArray("articles");

            for (int i = 0; i < articlesArray.length(); i++) {
                JSONObject articleObj = articlesArray.getJSONObject(i);

                String title = articleObj.optString("title", "No Title");
                String description = articleObj.optString("description", "No Description");
                String imageUrl = articleObj.optString("image", "");
                String url = articleObj.optString("url", "");

                articles.add(new NewsArticle(title, description, imageUrl, url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public interface NewsResponseListener {
        void onResponse(List<NewsArticle> response);
        void onError(VolleyError error);
    }

}
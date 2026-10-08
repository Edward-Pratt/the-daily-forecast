package com.edwardpratt.thedailyforecast.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.edwardpratt.thedailyforecast.BuildConfig
import com.edwardpratt.thedailyforecast.model.NewsArticle
import org.json.JSONException
import org.json.JSONObject

class NewsApi(context: Context) {
    private val requestQueue =
        Volley.newRequestQueue(context.applicationContext)

    // Fetches news articles from the GNews API
    fun fetchNews(listener: NewsResponseListener) {
        val request = JsonObjectRequest(
            Request.Method.GET, BASE_URL, null,
            { response ->
                val newsArticles = parseJson(response)
                listener.onResponse(newsArticles)
            },
            { error -> listener.onError(error) })

        requestQueue.add(request)
    }


    // Parses the JSON response from the GNews API
    private fun parseJson(response: JSONObject): List<NewsArticle> {
        val articles: MutableList<NewsArticle> = ArrayList()
        try {
            val articlesArray = response.getJSONArray("articles")

            for (i in 0..<articlesArray.length()) {
                val articleObj = articlesArray.getJSONObject(i)

                val title = articleObj.optString("title", "No Title")
                val description = articleObj.optString("description", "No Description")
                val imageUrl = articleObj.optString("image", "")
                val url = articleObj.optString("url", "")

                articles.add(NewsArticle(title, description, imageUrl, url))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return articles
    }

    // Interface for handling the response from the API
    interface NewsResponseListener {
        fun onResponse(response: List<NewsArticle>?)
        fun onError(error: VolleyError?)
    }

    companion object {
        private val API_KEY = BuildConfig.GNEWS_API_KEY
        private val BASE_URL =
            "https://gnews.io/api/v4/top-headlines?category=general&lang=en&country=gb&max=10&apikey=$API_KEY"
    }
}
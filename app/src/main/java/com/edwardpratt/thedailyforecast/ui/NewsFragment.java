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
import com.edwardpratt.thedailyforecast.model.NewsArticle;
import com.edwardpratt.thedailyforecast.network.NewsApi;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private NewsAdapter newsAdapter;
    private List<NewsArticle> newsList;
    private SwipeRefreshLayout swipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.newsRecyclerView);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(requireContext(), newsList);
        recyclerView.setAdapter(newsAdapter);

        fetchNewsData();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchNewsData();
            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    private void fetchNewsData(){
        NewsApi apiService = new NewsApi(requireContext());

        apiService.fetchNews(new NewsApi.NewsResponseListener(){
            @Override
            public void onResponse(List<NewsArticle> articles){
                newsList.clear();
                newsList.addAll(articles);
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error){
                Toast.makeText(getContext(), "Failed to fetch news", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

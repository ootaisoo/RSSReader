package com.example.administrator.rssreader.view.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.administrator.rssreader.NewsItem;
import com.example.administrator.rssreader.R;
import com.example.administrator.rssreader.presenter.MainFragmentPresenter;
import com.example.administrator.rssreader.view.MainView;
import com.example.administrator.rssreader.view.adapters.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment<MainFragmentPresenter> implements MainView {

    public static final String LOG_TAG = MainFragment.class.getName();

    public NewsAdapter newsAdapter;
    private RecyclerView mainRecyclerView;
    private ProgressBar progressBar;
    private TextView emptyTextView;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void inject() {
            setPresenter(new MainFragmentPresenter(this));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainFragmentView = inflater.inflate(R.layout.main_fragment, container, false);

        swipeContainer = mainFragmentView.findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getArguments() != null) {
                    loadNewData();
                }
                swipeContainer.setRefreshing(false);
            }
        });

        progressBar = mainFragmentView.findViewById(R.id.progress_bar);
        emptyTextView = mainFragmentView.findViewById(R.id.empty_view);

        List<NewsItem> feedItems = new ArrayList<>();
        newsAdapter = new NewsAdapter(feedItems, getActivity());

        mainRecyclerView = mainFragmentView.findViewById(R.id.main_recycler);
        mainRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(newsAdapter);

        if (getArguments() != null) {
            loadNewData();
        }

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        mainRecyclerView.addItemDecoration(dividerItemDecoration);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText(R.string.choose_feed);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet_connection);
        }
        return mainFragmentView;
    }

    @Override
    public void onFeedsLoaded(List<NewsItem> feedItems){
        progressBar.setVisibility(View.GONE);
        newsAdapter.addAll(feedItems);
        mainRecyclerView.setAdapter(newsAdapter);
    }

    public void loadNewData(){
        if (newsAdapter != null){
            newsAdapter.clear();
        }
        emptyTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getPresenter().loadNews(getArguments().getString("rssUrl"));
    }
}

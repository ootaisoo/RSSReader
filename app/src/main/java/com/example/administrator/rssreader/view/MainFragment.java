package com.example.administrator.rssreader.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.rssreader.presenter.MainFragmentPresenter;
import com.example.administrator.rssreader.NewsAdapter;
import com.example.administrator.rssreader.R;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

/**
 * Created by Administrator on 24.01.2018.
 */

public class MainFragment extends BaseFragment implements MainView {

    public static final String LOG_TAG = MainFragment.class.getName();

    String rssUrl;
    public NewsAdapter newsAdapter;
    private RecyclerView mainRecyclerView;
    private ProgressBar progressBar;
    private TextView emptyTextView;
    MainFragmentPresenter mainFragmentPresenter;
    private SwipeRefreshLayout swipeContainer;
    List<Element> feedItems;

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
        emptyTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        mainFragmentPresenter = new MainFragmentPresenter(this);
        mainFragmentPresenter.loadNews(rssUrl);
    }

    @Override
    protected void inject() {
        if (getPresenter() == null) {
            setPresenter(mainFragmentPresenter = new MainFragmentPresenter(this));
        }
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

        swipeContainer = (SwipeRefreshLayout) mainFragmentView.findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainFragmentPresenter.loadNews(rssUrl);
                swipeContainer.setRefreshing(false);
            }
        });

        progressBar = (ProgressBar) mainFragmentView.findViewById(R.id.progress_bar);
        emptyTextView = (TextView) mainFragmentView.findViewById(R.id.empty_view);

        feedItems = new ArrayList<>();
        newsAdapter = new NewsAdapter(feedItems, getActivity());

        mainRecyclerView = mainFragmentView.findViewById(R.id.main_recycler);
        mainRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(newsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        mainRecyclerView.addItemDecoration(dividerItemDecoration);

        mainFragmentPresenter = new MainFragmentPresenter(this);

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
    public void onFeedsLoaded(List<Element> feedItems){
        this.feedItems = feedItems;
        if (newsAdapter != null){
            newsAdapter.clear();
        }
        progressBar.setVisibility(View.GONE);
        newsAdapter.addAll(this.feedItems);
        mainRecyclerView.setAdapter(newsAdapter);
    }
}

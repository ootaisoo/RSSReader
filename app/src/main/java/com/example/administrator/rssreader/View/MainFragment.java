package com.example.administrator.rssreader.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.rssreader.Presenter.MainFragmentPresenter;
import com.example.administrator.rssreader.Presenter.NewsAdapter;
import com.example.administrator.rssreader.Presenter.NewsLoader;
import com.example.administrator.rssreader.R;

import java.util.List;

import org.jdom2.Element;

/**
 * Created by Administrator on 24.01.2018.
 */

public class MainFragment extends Fragment implements MainView {

    public static final String LOG_TAG = MainFragment.class.getName();

    public NewsAdapter newsAdapter;
    private String rssUrl;
    private RecyclerView mainRecyclerView;
    private ProgressBar progressBar;
    private TextView emptyTextView;
    MainFragmentPresenter mainFragmentPresenter;
    List<Element> feedItems;

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
        if (rssUrl == null){
            Log.e(LOG_TAG, "rssUrl == null");
        }
        mainFragmentPresenter = new MainFragmentPresenter(this);
        feedItems = mainFragmentPresenter.loadNews(rssUrl);

        newsAdapter = new NewsAdapter(feedItems, getActivity());
        mainRecyclerView.setAdapter(newsAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainFragmentView = inflater.inflate(R.layout.main_fragment, container, false);

        progressBar = (ProgressBar) mainFragmentView.findViewById(R.id.progress_bar);
        emptyTextView = (TextView) mainFragmentView.findViewById(R.id.empty_view);

        mainRecyclerView = mainFragmentView.findViewById(R.id.main_recycler);
        mainRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mainRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        mainRecyclerView.addItemDecoration(dividerItemDecoration);

        mainFragmentPresenter = new MainFragmentPresenter(this);

//        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
//        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
//            emptyTextView.setVisibility(View.GONE);
//            progressBar.setVisibility(View.VISIBLE);
//            feedItems = mainFragmentPresenter.loadNews(rssUrl);
//            progressBar.setVisibility(View.GONE);
//        } else {
//            progressBar.setVisibility(View.GONE);
//            emptyTextView.setText(R.string.no_internet_connection);
//        }

        if (feedItems == null || feedItems.isEmpty()){
            emptyTextView.setText(R.string.choose_feed);
            mainRecyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            mainRecyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            newsAdapter = new NewsAdapter(feedItems, getActivity());
            mainRecyclerView.setAdapter(newsAdapter);
        }
        return mainFragmentView;
    }
}

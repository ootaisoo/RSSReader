package com.example.administrator.rssreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

/**
 * Created by Administrator on 24.01.2018.
 */

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Element>> {

    public static final String LOG_TAG = MainFragment.class.getName();

    private NewsAdapter newsAdapter;
    private String rssUrl;
    private RecyclerView mainRecyclerView;
    private ProgressBar progressBar;
    private TextView emptyTextView;

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainFragmentView = inflater.inflate(R.layout.main_fragment, container, false);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        progressBar = (ProgressBar) mainFragmentView.findViewById(R.id.progress_bar);
        emptyTextView = (TextView) mainFragmentView.findViewById(R.id.empty_view);

        mainRecyclerView = mainFragmentView.findViewById(R.id.main_recycler);
        mainRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mainRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        mainRecyclerView.addItemDecoration(dividerItemDecoration);

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            getLoaderManager().initLoader(0, null, MainFragment.this);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet_connection);
        }

        return mainFragmentView;
    }

    @Override
    public Loader<List<Element>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG, "onCreateLoader");
        emptyTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        return new NewsLoader(rssUrl, getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Element>> loader, List<Element> feedItems) {
        Log.e(LOG_TAG, "onLoadFinished");

        progressBar.setVisibility(View.GONE);

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
    }

    @Override
    public void onLoaderReset(Loader<List<Element>> loader) {

    }
}

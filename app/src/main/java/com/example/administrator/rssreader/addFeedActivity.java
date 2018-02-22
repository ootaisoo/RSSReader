package com.example.administrator.rssreader;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 25.01.2018.
 */

public class addFeedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ProposedFeedItem>> {

    public static final String LOG_TAG = addFeedActivity.class.getName();

    private ProposedFeedsUdapter proposedFeedsUdapter;
    private EditText urlText;
    private String baseUrl;
    private RecyclerView feedsRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_feed_activity);
        urlText = findViewById(R.id.edit_search);

//        Performs rss url search via keyboard search button.
        urlText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        progressBar = findViewById(R.id.progress_bar_add_feed_activity);
        progressBar.setVisibility(View.GONE);

        feedsRecyclerView = findViewById(R.id.feed_recycler_view);
        feedsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        feedsRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        feedsRecyclerView.addItemDecoration(dividerItemDecoration);

        ImageButton searchURLButton = findViewById(R.id.search_for_rss_button);
        searchURLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });
    }

    @Override
    public Loader<List<ProposedFeedItem>> onCreateLoader(int i, Bundle bundle) {
        return new ProposedFeedItemLoader(baseUrl,this);
    }

    @Override
    public void onLoadFinished(Loader<List<ProposedFeedItem>> loader, List<ProposedFeedItem> proposedFeedItemList) {
        progressBar.setVisibility(View.GONE);
        if (proposedFeedItemList != null && !proposedFeedItemList.isEmpty()){
            proposedFeedsUdapter = new ProposedFeedsUdapter(proposedFeedItemList, addFeedActivity.this);
            feedsRecyclerView.setAdapter(proposedFeedsUdapter);
        } else {
            Toast.makeText(this, R.string.invalid_url, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ProposedFeedItem>> loader) {
    }

    //is httpsUrl redundant?
    public String makeValidUrl(String url) throws MalformedURLException{
        if (!URLUtil.isValidUrl(url)) {
            String httpUrl = "http://" + url;
            if (!URLUtil.isValidUrl(httpUrl)) {
                String httpsUrl = "https://" + url;
                if (!URLUtil.isValidUrl(httpsUrl)) {
                    Toast.makeText(this, R.string.invalid_url, Toast.LENGTH_SHORT).show();
                    return null;
                } else {
                    return httpsUrl;
                }
            } else {
                return httpUrl;
            }
        } else {
            return url;
        }
    }

    private void performSearch(){
        InputMethodManager mgr = (InputMethodManager) getSystemService(addFeedActivity.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(urlText.getWindowToken(), 0);
        if (proposedFeedsUdapter != null) {
            proposedFeedsUdapter.clear();
        }

        progressBar.setVisibility(View.VISIBLE);

        baseUrl = urlText.getText().toString().trim();
        try {
            baseUrl = makeValidUrl(baseUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        getLoaderManager().restartLoader(0, null, addFeedActivity.this);
    }
}

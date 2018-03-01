package com.example.administrator.rssreader.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rssreader.presenter.AddFeedPresenter;
import com.example.administrator.rssreader.ProposedFeedItem;
import com.example.administrator.rssreader.ProposedFeedsUdapter;
import com.example.administrator.rssreader.R;
import com.example.administrator.rssreader.Utils;
import com.example.administrator.rssreader.presenter.DrawerFragmentPresenter;

import java.net.MalformedURLException;
import java.util.List;


/**
 * Created by Administrator on 25.01.2018.
 */

public class AddFeedActivity extends BaseActivity<AddFeedPresenter> implements AddFeedView {

    public static final String LOG_TAG = AddFeedActivity.class.getName();

    private AddFeedPresenter addFeedPresenter;
    private ProposedFeedsUdapter proposedFeedsUdapter;
    private EditText urlText;
    private String baseUrl;
    private RecyclerView feedsRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void inject() {
        if (getPresenter() == null) {
            setPresenter(addFeedPresenter = new AddFeedPresenter(this));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_feed_activity);
        urlText = findViewById(R.id.edit_search);

        addFeedPresenter = new AddFeedPresenter(this);

//        Performs rss url search via keyboard search button.
        urlText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    addFeedPresenter.performFeedSearch();
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
                addFeedPresenter.performFeedSearch();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onProposedFeedsLoaded(List<ProposedFeedItem> proposedFeedItemList) {
        progressBar.setVisibility(View.GONE);
        if (proposedFeedItemList != null && !proposedFeedItemList.isEmpty()){
            proposedFeedsUdapter = new ProposedFeedsUdapter(proposedFeedItemList, AddFeedActivity.this);
            feedsRecyclerView.setAdapter(proposedFeedsUdapter);
        } else {
            Toast.makeText(this, R.string.invalid_url, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void performFeedSearch(){
        InputMethodManager mgr = (InputMethodManager) getSystemService(AddFeedActivity.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(urlText.getWindowToken(), 0);
        if (proposedFeedsUdapter != null) {
            proposedFeedsUdapter.clear();
        }

        progressBar.setVisibility(View.VISIBLE);

        baseUrl = urlText.getText().toString().trim();
        try {
            baseUrl = Utils.makeValidUrl(baseUrl, this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        addFeedPresenter.loadProposedFeeds(baseUrl);
    }
}

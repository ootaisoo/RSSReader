package com.example.administrator.rssreader.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.rssreader.R;
import com.example.administrator.rssreader.presenter.NewsPresenter;
import com.example.administrator.rssreader.view.NewsView;
import com.example.administrator.rssreader.view.utils.Utils;

public class NewsActivity extends BaseActivity<NewsPresenter> implements NewsView {

    public static final String LOG_TAG = NewsActivity.class.getName();

    @Override
    protected void inject() {
        if (getPresenter() == null) {
            setPresenter(new NewsPresenter(this));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        Intent intent = getIntent();
        String title = intent.getStringExtra("newsTitle");
        final String resource = intent.getStringExtra("newsResource");
        String publishingDate = intent.getStringExtra("newsPublishingDate");
        String description = intent.getStringExtra("newsDescription");
        String imageUrl = intent.getStringExtra("newsImage");

        TextView titleTextView = findViewById(R.id.news_title);
        titleTextView.setText(title);

        TextView resourceTextView = findViewById(R.id.news_url);
        resourceTextView.setText(resource);

        TextView publishingDateTextView = findViewById(R.id.news_publishing_date);
        publishingDateTextView.setText(publishingDate);

        TextView descriptionTextView = findViewById(R.id.news_description);
        descriptionTextView.setText(description);

        ImageView newsImageView = findViewById(R.id.news_image);
        Utils.setImageFromUrl(this, newsImageView, imageUrl);

        Button goToSiteButton = findViewById(R.id.go_to_site_button);
        goToSiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(resource);
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, uri);
                if (urlIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(urlIntent);
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

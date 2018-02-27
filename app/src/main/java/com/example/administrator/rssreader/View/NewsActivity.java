package com.example.administrator.rssreader.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.rssreader.Presenter.NewsPresenter;
import com.example.administrator.rssreader.Presenter.Utils;
import com.example.administrator.rssreader.R;

/**
 * Created by Administrator on 16.02.2018.
 */

public class NewsActivity extends AppCompatActivity implements NewsView {

    public static final String LOG_TAG = NewsActivity.class.getName();

    NewsPresenter newsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        newsPresenter = new NewsPresenter(this);

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
                newsPresenter.goToSite(resource);
            }
        });
    }

 // is it right to place this method here not in Utils class?
    @Override
    public void goToSite(String url) {
        Uri uri = Uri.parse(url);
        Intent urlIntent = new Intent(Intent.ACTION_VIEW, uri);
        if (urlIntent.resolveActivity(getPackageManager()) != null){
            startActivity(urlIntent);
        }
    }
}

package com.example.administrator.rssreader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 16.02.2018.
 */

public class NewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        Intent intent = getIntent();
        String title = intent.getStringExtra("newsTitle");
        final String resource = intent.getStringExtra("newsResource");
        String publishingDate = intent.getStringExtra("newsPublishingDate");
        String description = intent.getStringExtra("newsDescription");
        byte[] imageBytes = intent.getByteArrayExtra("newsImage");
        Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        TextView titleTextView = findViewById(R.id.news_title);
        titleTextView.setText(title);

        TextView resourceTextView = findViewById(R.id.news_url);
        resourceTextView.setText(resource);

        TextView publishingDateTextView = findViewById(R.id.news_publishing_date);
        publishingDateTextView.setText(publishingDate);

        TextView descriptionTextView = findViewById(R.id.news_description);
        descriptionTextView.setText(description);

        ImageView bmpImageView = findViewById(R.id.news_image);
        bmpImageView.setImageBitmap(bmp);

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

    }
}

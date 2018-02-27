package com.example.administrator.rssreader.Presenter;

import android.graphics.Bitmap;
import android.widget.Button;

/**
 * Created by Administrator on 25.01.2018.
 */

public class ProposedFeedItem {

    private String feedName;
    private String feedUrl;
    private Bitmap feedImage;

    public ProposedFeedItem(String feedName, String feedUrl, Bitmap feedImage) {
        this.feedName = feedName;
        this.feedUrl = feedUrl;
        this.feedImage = feedImage;
    }

    public String getFeedName() {
        return feedName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public Bitmap getFeedImage() {
        return feedImage;
    }
}

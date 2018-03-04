package com.example.administrator.rssreader;

import android.graphics.Bitmap;

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

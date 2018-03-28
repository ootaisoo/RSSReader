package com.example.administrator.rssreader;

import android.util.Log;

import com.example.administrator.rssreader.view.adapters.ProposedFeedsUdapter;

public class ProposedFeedItem {

    public static final String LOG_TAG = ProposedFeedItem.class.getName();

    private String feedName;
    private String feedUrl;
    private String feedImage;

    public ProposedFeedItem(String feedName, String feedUrl, String feedImage) {
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

    public String getFeedImage() {
        return feedImage;
    }
}

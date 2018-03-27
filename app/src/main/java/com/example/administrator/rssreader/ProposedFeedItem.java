package com.example.administrator.rssreader;

public class ProposedFeedItem {

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

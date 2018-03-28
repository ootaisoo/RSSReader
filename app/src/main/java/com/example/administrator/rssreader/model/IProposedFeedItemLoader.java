package com.example.administrator.rssreader.model;

public interface IProposedFeedItemLoader {

    public void loadProposedFeedItems(String url, final ProposedFeedItemLoader.ProposedFeedsListener listener);
}

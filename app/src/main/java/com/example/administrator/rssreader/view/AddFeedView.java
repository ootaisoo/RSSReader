package com.example.administrator.rssreader.view;

import com.example.administrator.rssreader.ProposedFeedItem;

import java.util.List;

public interface AddFeedView extends MvpView {
    void performFeedSearch();
    void onProposedFeedsLoaded(List<ProposedFeedItem> proposedFeedItemList);
}

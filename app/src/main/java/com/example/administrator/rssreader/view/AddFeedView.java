package com.example.administrator.rssreader.view;

import com.example.administrator.rssreader.ProposedFeedItem;

import java.util.List;

/**
 * Created by Administrator on 25.02.2018.
 */

public interface AddFeedView {
    void performFeedSearch();
    void onProposedFeedsLoaded(List<ProposedFeedItem> proposedFeedItemList);
}

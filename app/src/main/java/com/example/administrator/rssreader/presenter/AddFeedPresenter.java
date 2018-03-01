package com.example.administrator.rssreader.presenter;

import com.example.administrator.rssreader.ProposedFeedItem;
import com.example.administrator.rssreader.ProposedFeedItemLoader;
import com.example.administrator.rssreader.view.AddFeedView;

import java.util.List;

/**
 * Created by Administrator on 25.02.2018.
 */

public class AddFeedPresenter extends BasePresenter {

    AddFeedView addFeedView;

    public AddFeedPresenter(AddFeedView addFeedView) {
        super(addFeedView);
        this.addFeedView = addFeedView;
    }

    public void performFeedSearch(){
        addFeedView.performFeedSearch();
    }

    public void loadProposedFeeds(String url){
        ProposedFeedItemLoader proposedFeedItemLoader = new ProposedFeedItemLoader();
        proposedFeedItemLoader.loadProposedFeedItems(url, proposedFeedsListener);
    }

    ProposedFeedItemLoader.ProposedFeedsListener proposedFeedsListener = new ProposedFeedItemLoader.ProposedFeedsListener() {
        @Override
        public void onLoaded(List<ProposedFeedItem> items) {
            addFeedView.onProposedFeedsLoaded(items);
        }
    };
}

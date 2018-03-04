package com.example.administrator.rssreader.presenter;

import com.example.administrator.rssreader.ProposedFeedItem;
import com.example.administrator.rssreader.view.AddFeedView;
import com.example.administrator.rssreader.view.utils.ProposedFeedItemLoader;

import java.util.List;

public class AddFeedPresenter extends BasePresenter<AddFeedView> {

    public AddFeedPresenter(AddFeedView addFeedView) {
        super(addFeedView);
    }

    public void performFeedSearch(){
        getView().performFeedSearch();
    }

    public void loadProposedFeeds(String url){
        ProposedFeedItemLoader proposedFeedItemLoader = new ProposedFeedItemLoader();
        proposedFeedItemLoader.loadProposedFeedItems(url, proposedFeedsListener);
    }

    private ProposedFeedItemLoader.ProposedFeedsListener proposedFeedsListener = new ProposedFeedItemLoader.ProposedFeedsListener() {
        @Override
        public void onLoaded(List<ProposedFeedItem> items) {
            getView().onProposedFeedsLoaded(items);
        }
    };
}

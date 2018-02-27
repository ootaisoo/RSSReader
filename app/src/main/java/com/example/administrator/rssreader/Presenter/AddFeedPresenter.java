package com.example.administrator.rssreader.Presenter;

import com.example.administrator.rssreader.View.AddFeedView;

/**
 * Created by Administrator on 25.02.2018.
 */

public class AddFeedPresenter {

    AddFeedView addFeedView;

    public AddFeedPresenter(AddFeedView addFeedView) {
        this.addFeedView = addFeedView;
    }

    public void performFeedSearch(){
        addFeedView.performFeedSearch();
    }
}

package com.example.administrator.rssreader.presenter;

import com.example.administrator.rssreader.view.NewsView;

/**
 * Created by Administrator on 26.02.2018.
 */

public class NewsPresenter extends BasePresenter {

    private NewsView newsView;

    public NewsPresenter(NewsView newsView) {
        this.newsView = newsView;
    }

    public void goToSite(String url){
        newsView.goToSite(url);
    }
}

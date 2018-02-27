package com.example.administrator.rssreader.Presenter;

import android.content.Context;

import com.example.administrator.rssreader.View.NewsView;

/**
 * Created by Administrator on 26.02.2018.
 */

public class NewsPresenter {

    NewsView newsView;

    public NewsPresenter(NewsView newsView) {
        this.newsView = newsView;
    }

    public void goToSite(String url){
        newsView.goToSite(url);
    }
}

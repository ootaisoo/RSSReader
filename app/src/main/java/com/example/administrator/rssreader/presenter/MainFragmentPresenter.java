package com.example.administrator.rssreader.presenter;

import android.util.Log;

import com.example.administrator.rssreader.NewsLoader;
import com.example.administrator.rssreader.view.MainView;

import org.jdom2.Element;

import java.util.List;

/**
 * Created by Administrator on 25.02.2018.
 */

public class MainFragmentPresenter extends BasePresenter {
    public static final String LOG_TAG = MainFragmentPresenter.class.getName();

    private MainView view;

    public MainFragmentPresenter(MainView view) {
        super(view);
        this.view = view;
    }

    public void loadNews(String url){
        Log.e(LOG_TAG, "MainFragmentPresenter");
        NewsLoader newsLoader = new NewsLoader();
        newsLoader.loadItems(url, feedsListener);
    }

    NewsLoader.FeedsListener feedsListener = new NewsLoader.FeedsListener() {
        @Override
        public void onLoaded(List<Element> feeds) {
            view.onFeedsLoaded(feeds);
        }

        @Override
        public void onError(Exception e) {

        }
    };
}

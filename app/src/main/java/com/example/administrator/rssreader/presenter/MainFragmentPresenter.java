package com.example.administrator.rssreader.presenter;

import android.util.Log;

import com.example.administrator.rssreader.NewsItem;
import com.example.administrator.rssreader.view.MainView;
import com.example.administrator.rssreader.view.utils.INewsLoader;
import com.example.administrator.rssreader.view.utils.NewsLoader;

import java.util.List;

public class MainFragmentPresenter extends BasePresenter<MainView> {
    public static final String LOG_TAG = MainFragmentPresenter.class.getName();

    public MainFragmentPresenter(MainView view) {
        super(view);
    }

    public void loadNews(String url){
        INewsLoader newsLoader = new NewsLoader();
        newsLoader.loadItems(url, feedsListener);
    }

    private NewsLoader.FeedsListener feedsListener = new NewsLoader.FeedsListener() {
        @Override
        public void onLoaded(List<NewsItem> feeds) {
            if (feeds.isEmpty()){
                Log.e(LOG_TAG, "feeds is empty");
            }
            getView().onFeedsLoaded(feeds);
        }
    };
}

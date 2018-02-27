package com.example.administrator.rssreader.Presenter;

import android.util.Log;

import com.example.administrator.rssreader.View.MainView;

import org.jdom2.Element;

import java.util.List;

/**
 * Created by Administrator on 25.02.2018.
 */

public class MainFragmentPresenter {
    public static final String LOG_TAG = MainFragmentPresenter.class.getName();

    private MainView view;

    public MainFragmentPresenter(MainView view) {
        this.view = view;
    }

    public List<Element> loadNews(String url){
        Log.e(LOG_TAG, "MainFragmentPresenter");
        NewsLoader newsLoader = new NewsLoader(url);
        newsLoader.loadItems();
        return newsLoader.feedItems;
    }
}

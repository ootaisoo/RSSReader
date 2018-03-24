package com.example.administrator.rssreader.view;


import com.example.administrator.rssreader.NewsItem;
import com.example.administrator.rssreader.NewsItemList;

import org.jdom2.Element;

import java.util.List;

public interface MainView extends MvpView {
    void onFeedsLoaded(List<NewsItem> items);
}

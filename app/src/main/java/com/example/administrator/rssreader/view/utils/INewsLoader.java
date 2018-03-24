package com.example.administrator.rssreader.view.utils;

public interface INewsLoader {

    void loadItems(String url, final NewsLoader.FeedsListener listener);
}

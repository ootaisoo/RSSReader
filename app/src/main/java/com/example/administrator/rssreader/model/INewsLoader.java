package com.example.administrator.rssreader.model;

public interface INewsLoader {

    void loadItems(String url, final NewsLoader.FeedsListener listener);
}

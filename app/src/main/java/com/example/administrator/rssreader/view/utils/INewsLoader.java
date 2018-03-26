package com.example.administrator.rssreader.view.utils;

import java.net.MalformedURLException;

public interface INewsLoader {

    void loadItems(String url, final NewsLoader.FeedsListener listener);
}

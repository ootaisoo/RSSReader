package com.example.administrator.rssreader.view;

import org.jdom2.Element;

import java.util.List;

/**
 * Created by Administrator on 24.02.2018.
 */

public interface MainView {
    void onFeedsLoaded(List<Element> items);
}

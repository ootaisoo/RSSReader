package com.example.administrator.rssreader.view;

import org.jdom2.Element;

import java.util.List;

public interface MainView extends MvpView {
    void onFeedsLoaded(List<Element> items);
}

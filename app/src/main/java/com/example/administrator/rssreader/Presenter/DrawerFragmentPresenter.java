package com.example.administrator.rssreader.Presenter;

import com.example.administrator.rssreader.View.DrawerView;

/**
 * Created by Administrator on 25.02.2018.
 */

public class DrawerFragmentPresenter {

    private DrawerView drawerView;

    public DrawerFragmentPresenter(DrawerView drawerView) {
        this.drawerView = drawerView;
    }

    public void initCursorLoader(){
        drawerView.initCursorLoader();
    }

    public void callAddFeedActivity(){
        drawerView.callAddFeedActivity();
    }
}

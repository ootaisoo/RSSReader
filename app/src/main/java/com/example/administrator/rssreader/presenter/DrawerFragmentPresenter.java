package com.example.administrator.rssreader.presenter;

import android.database.Cursor;

import com.example.administrator.rssreader.FeedsFromDbLoader;
import com.example.administrator.rssreader.view.DrawerView;

/**
 * Created by Administrator on 25.02.2018.
 */

public class DrawerFragmentPresenter extends BasePresenter {

    private DrawerView drawerView;

    public DrawerFragmentPresenter(DrawerView drawerView) {
        super(drawerView);
        this.drawerView = drawerView;
    }

    public void callAddFeedActivity(){
        drawerView.callAddFeedActivity();
    }

    public void loadFromDb(){
        FeedsFromDbLoader feedsFromDbLoader = new FeedsFromDbLoader();
        feedsFromDbLoader.loadFeedsFromDb(drawerView.getViewContext(), feedsListener);
    }

    FeedsFromDbLoader.FeedsFromDbListener feedsListener = new FeedsFromDbLoader.FeedsFromDbListener() {
        @Override
        public void onLoaded(Cursor cursor) {
            drawerView.onCursorLoaded(cursor);
        }
    };
}

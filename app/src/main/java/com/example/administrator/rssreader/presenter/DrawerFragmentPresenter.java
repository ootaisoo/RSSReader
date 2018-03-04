package com.example.administrator.rssreader.presenter;

import android.database.Cursor;

import com.example.administrator.rssreader.view.DrawerView;
import com.example.administrator.rssreader.view.utils.FeedsFromDbLoader;

public class DrawerFragmentPresenter extends BasePresenter<DrawerView> {

    public DrawerFragmentPresenter(DrawerView drawerView) {
        super(drawerView);
    }

    public void callAddFeedActivity(){
        getView().callAddFeedActivity();
    }

    public void loadFromDb(){
        FeedsFromDbLoader feedsFromDbLoader = new FeedsFromDbLoader();
        feedsFromDbLoader.loadFeedsFromDb(getView().getViewContext(), feedsListener);
    }

    private FeedsFromDbLoader.FeedsFromDbListener feedsListener = new FeedsFromDbLoader.FeedsFromDbListener() {
        @Override
        public void onLoaded(Cursor cursor) {
            getView().onCursorLoaded(cursor);
        }
    };
}

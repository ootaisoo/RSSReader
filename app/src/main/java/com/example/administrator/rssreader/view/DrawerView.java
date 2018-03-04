package com.example.administrator.rssreader.view;

import android.content.Context;
import android.database.Cursor;

public interface DrawerView extends MvpView {
    void callAddFeedActivity();
    void onCursorLoaded(Cursor cursor);
    Context getViewContext();
}

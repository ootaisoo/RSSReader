package com.example.administrator.rssreader.view;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by Administrator on 25.02.2018.
 */

public interface DrawerView extends MvpView {
    void callAddFeedActivity();
    void onCursorLoaded(Cursor cursor);
    Context getViewContext();
}

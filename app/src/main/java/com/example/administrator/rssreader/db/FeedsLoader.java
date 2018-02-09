package com.example.administrator.rssreader.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Created by Administrator on 05.02.2018.
 */

public class FeedsLoader extends AsyncTaskLoader<Cursor> {

    public static final String LOG_TAG = FeedsLoader.class.getName();

    private Cursor cursor;
    final ForceLoadContentObserver observer;

    public FeedsLoader(Context context) {
        super(context);
        this.observer = new ForceLoadContentObserver();
    }

    @Override
    protected void onStartLoading() {
        if (this.cursor != null) {
            deliverResult(this.cursor);
        }

        if (takeContentChanged() || this.cursor == null) {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        Log.e(LOG_TAG, "loadInBackground()");
        FeedDbHelper dbHelper = new FeedDbHelper(getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        cursor = database.query(FeedsContract.FeedEntries.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null){
            cursor.registerContentObserver(this.observer);
            cursor.setNotificationUri(getContext().getContentResolver(), FeedsContract.FeedEntries.URI);
        }
        return cursor;
    }
}

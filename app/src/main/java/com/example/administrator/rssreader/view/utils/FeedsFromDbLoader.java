package com.example.administrator.rssreader.view.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.example.administrator.rssreader.model.FeedDbHelper;
import com.example.administrator.rssreader.model.FeedsContract;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 05.02.2018.
 */

public class FeedsFromDbLoader {

    public static final String LOG_TAG = FeedsFromDbLoader.class.getName();

    public interface FeedsFromDbListener{
        void onLoaded(Cursor cursor);
    }

    public void loadFeedsFromDb(Context context, FeedsFromDbListener listener){
        DbLoader dbLoader = new DbLoader(context, listener);
        Thread thread = new Thread(dbLoader);
        thread.start();
    }

    static class DbLoader implements Runnable {
        ContentObserver observer;
        Handler handler = new Handler();
        private Cursor cursor;
        Context context;
        private final WeakReference<FeedsFromDbListener> listener;

        public DbLoader(Context context, FeedsFromDbListener listener) {
            this.context = context;
            this.listener = new WeakReference(listener);
            this.observer = new ContentObserver(handler) {

                @Override
                public void onChange(boolean selfChange) {
                    super.onChange(selfChange);
                    run();
                }
            };
        }

        @Override
        public void run() {
            final FeedsFromDbListener feedsListener = listener.get();
            FeedDbHelper dbHelper = new FeedDbHelper(context);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            cursor = database.query(FeedsContract.FeedEntries.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null){
                cursor.registerContentObserver(this.observer);
                cursor.setNotificationUri(context.getContentResolver(), FeedsContract.FeedEntries.URI);
            }
            if (feedsListener != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        feedsListener.onLoaded(cursor);
                    }
                });
            }
        }
    }
}

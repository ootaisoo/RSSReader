package com.example.administrator.rssreader;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.administrator.rssreader.db.FeedsContract;

import java.net.URL;

/**
 * Created by Administrator on 25.01.2018.
 */

public class FeedItem {

    public static final String LOG_TAG = FeedItem.class.getName();

    private String name;
    private Bitmap image;

    public FeedItem(String name, Bitmap image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }

    public static FeedItem fromCursor(Cursor cursor){
        String name = cursor.getString(cursor.getColumnIndexOrThrow(FeedsContract.FeedEntries.FEED_NAME));
        byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(FeedsContract.FeedEntries.FEED_RESOURCE_IMAGE));
        Bitmap bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        Log.e(LOG_TAG, "fromCursor() method");
        return new FeedItem(name, bmp);
    }
}

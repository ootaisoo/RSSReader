package com.example.administrator.rssreader;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.administrator.rssreader.model.FeedsContract;

/**
 * Created by Administrator on 25.01.2018.
 */

public class FeedItem {

    public static final String LOG_TAG = FeedItem.class.getName();

    private String name;
    private Bitmap image;
    private String feedUrl;

    public FeedItem(String name, Bitmap image, String feedUrl) {
        this.name = name;
        this.image = image;
        this.feedUrl = feedUrl;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public static FeedItem fromCursor(Cursor cursor){
        String name = cursor.getString(cursor.getColumnIndexOrThrow(FeedsContract.FeedEntries.FEED_NAME));
        byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(FeedsContract.FeedEntries.FEED_RESOURCE_IMAGE));
        Bitmap bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        String url = cursor.getString(cursor.getColumnIndexOrThrow(FeedsContract.FeedEntries.FEED_URL));
        return new FeedItem(name, bmp, url);
    }
}

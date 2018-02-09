package com.example.administrator.rssreader.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 31.01.2018.
 */

public final class FeedsContract {
    public FeedsContract() {}


    public static final class FeedEntries implements BaseColumns{
        public static final Uri URI = Uri.parse("sqlite://com.example.administrator.rssreader/feeds");

        public static final String TABLE_NAME = "feeds";

        public static final String _ID = BaseColumns._ID;
        public static final String FEED_NAME = "feed_name";
        public static final String FEED_RESOURCE_IMAGE = "feed_image";
    }
}

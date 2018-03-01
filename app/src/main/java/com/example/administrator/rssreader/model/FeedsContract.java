package com.example.administrator.rssreader.model;

import android.net.Uri;
import android.provider.BaseColumns;

public final class FeedsContract {
    public FeedsContract() {}

    public static final class FeedEntries implements BaseColumns{
        public static final Uri URI = Uri.parse("sqlite://com.example.administrator.rssreader/feeds");

        public static final String TABLE_NAME = "feeds";

        public static final String _ID = BaseColumns._ID;
        public static final String FEED_NAME = "feed_name";
        public static final String FEED_RESOURCE_IMAGE = "feed_image";
        public static final String FEED_URL = "feed_url";
    }
}

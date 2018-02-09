package com.example.administrator.rssreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.rssreader.db.FeedsContract.FeedEntries;

/**
 * Created by Administrator on 01.02.2018.
 */

public class FeedDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "feedsdatabase.db";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedEntries.TABLE_NAME + " ("
            + FeedEntries._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FeedEntries.FEED_NAME + " TEXT UNIQUE, "
            + FeedEntries.FEED_RESOURCE_IMAGE + " BLOB);";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntries.TABLE_NAME;


    public FeedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}

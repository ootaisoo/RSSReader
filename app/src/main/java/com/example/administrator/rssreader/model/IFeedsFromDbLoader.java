package com.example.administrator.rssreader.model;

import android.content.Context;

public interface IFeedsFromDbLoader {
    public void loadFeedsFromDb(Context context, FeedsFromDbLoader.FeedsFromDbListener listener);
}

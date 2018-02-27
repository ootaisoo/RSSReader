package com.example.administrator.rssreader.Presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 26.02.2018.
 */

public class Utils {

    public static void setImageFromUrl(Context context, ImageView imageView, String url) {
        if (url != null) {
            Glide
                    .with(context)
                    .load(url)
                    .into(imageView);
        }
    }


}

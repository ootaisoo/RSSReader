package com.example.administrator.rssreader.view.utils;

import android.content.Context;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.rssreader.R;

import java.net.MalformedURLException;

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

    //is httpsUrl redundant?
    public static String buildUrl(String url, Context context) throws MalformedURLException {
        if (!URLUtil.isValidUrl(url)) {
            String httpUrl = "http://" + url;
            if (!URLUtil.isValidUrl(httpUrl)) {
                String httpsUrl = "https://" + url;
                if (!URLUtil.isValidUrl(httpsUrl)) {
                    Toast.makeText(context, R.string.invalid_url, Toast.LENGTH_SHORT).show();
                    return null;
                } else {
                    return httpsUrl;
                }
            } else {
                return httpUrl;
            }
        } else {
            return url;
        }
    }


}

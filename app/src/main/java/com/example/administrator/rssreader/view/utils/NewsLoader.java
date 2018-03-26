package com.example.administrator.rssreader.view.utils;

import android.net.Uri;
import android.os.Handler;
import android.util.Log;


import com.example.administrator.rssreader.NewsItem;
import com.example.administrator.rssreader.NewsItemList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public class NewsLoader implements INewsLoader {

    public static final String LOG_TAG = NewsLoader.class.getName();

    public interface FeedService {

        @GET("/{path}")
        Call<NewsItemList> fetchNewsItem(@Path("path") String path);
    }

    public interface FeedsListener {
        void onLoaded(List<NewsItem> feeds);
    }

    public void loadItems(String url, final FeedsListener listener){

        Call<NewsItemList> call = null;
        try {
            URL url1 = new URL(url);
            String baseUrl = url1.getProtocol() + "://" + url1.getHost() + "/";
            String path = url1.getPath();

//        String[] strings = url.split("/");
//        StringBuilder sb = new StringBuilder();
//        sb.append(strings[0])
//                .append("/")
//                .append(strings[1])
//                .append("/")
//                .append(strings[2])
//                .append("/");

//        String baseUrl = strings[0] + "/" + strings[1] + "/" + strings[2] + "/";
//        Log.e(LOG_TAG, baseUrl);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
    //                .client(client)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
            Log.e(LOG_TAG, "1");

            FeedService feedService = retrofit.create(FeedService.class);
            Log.e(LOG_TAG, "2");
            call = feedService.fetchNewsItem(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        call.enqueue(new Callback<NewsItemList>() {
            @Override
            public void onResponse(Call<NewsItemList> call, Response<NewsItemList> response) {
                Log.e(LOG_TAG, "3");
                    if (response.isSuccessful()) {
                        listener.onLoaded(response.body().getChannel().getNewsItems());
                    } else {
                        Log.e(LOG_TAG, String.valueOf(response.code()));

                    }
            }

            @Override
            public void onFailure(Call<NewsItemList> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });
    }
}


package com.example.administrator.rssreader.model;

import android.util.Log;

import com.example.administrator.rssreader.NewsItem;
import com.example.administrator.rssreader.NewsItemList;
import com.example.administrator.rssreader.view.utils.Utils;

import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

    URL feedUrl = Utils.parseUrl(url);

    String baseUrl = feedUrl.getProtocol() + "://" + feedUrl.getHost() + "/";
    String path = feedUrl.getPath();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        FeedService feedService = retrofit.create(FeedService.class);
        Call<NewsItemList> call = feedService.fetchNewsItem(path);

        call.enqueue(new Callback<NewsItemList>() {
            @Override
            public void onResponse(Call<NewsItemList> call, Response<NewsItemList> response) {
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


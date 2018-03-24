package com.example.administrator.rssreader.view.utils;

import android.os.Handler;


import com.example.administrator.rssreader.NewsItem;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public class NewsLoader {

    public static final String LOG_TAG = NewsLoader.class.getName();

    public interface FeedService {

        @GET()
        Call<List<NewsItem>> fetchNewsItem();
    }

    public interface FeedsListener {
        void onLoaded(List<NewsItem> feeds);
    }

    public void loadItems(String url, final FeedsListener listener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url + "/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        FeedService feedService = retrofit.create(FeedService.class);
        feedService.fetchNewsItem().enqueue(new Callback<List<NewsItem>>() {
            @Override
            public void onResponse(Call<List<NewsItem>> call, Response<List<NewsItem>> response) {
                listener.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<List<NewsItem>> call, Throwable t) {
                // пока не знаю, что здесь написать
            }
        });
    }
}




//    public void loadItems(String url, FeedsListener listener) {
//        FeedItemsLoader feedItemsLoader = new FeedItemsLoader(url, listener);
//        Thread thread = new Thread(feedItemsLoader);
//        thread.start();
//    }
//
//    public interface FeedsListener {
//        void onLoaded(List<Element> feeds);
//    }
//
//    static class FeedItemsLoader implements Runnable {
//        Handler handler = new Handler();
//        List<Element> feedItems;
//        private final String url;
//        private final WeakReference<FeedsListener> listener;
//
//        public FeedItemsLoader(String url, FeedsListener listener) {
//            this.url = url;
//            this.listener = new WeakReference(listener);
//        }
//
//        @Override
//        public void run() {
//            final FeedsListener feedsListener = listener.get();
//
//                feedItems = extractItemsFromRssUrl(url);
//
//                if (feedsListener != null) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            feedsListener.onLoaded(feedItems);
//                        }
//                    });
//                }
//        }
//
//        private List<Element> extractItemsFromRssUrl(String rssUrl) {
//
//            List<Element> feedItems = new ArrayList<>();
//            try {
//                URL url = new URL(rssUrl);
//                InputStream is = url.openStream();
//                Document doc = new SAXBuilder().build(is);
//                Element channel = doc.getRootElement().getChild("channel");
//                feedItems = channel.getChildren("item");
//
//            } catch (IOException | JDOMException e) {
//                e.printStackTrace();
//            }
//
//            return feedItems;
//        }
//    }
//}


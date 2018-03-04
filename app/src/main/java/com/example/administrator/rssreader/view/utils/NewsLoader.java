package com.example.administrator.rssreader.view.utils;

import android.os.Handler;


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

/**
 * Created by Administrator on 10.02.2018.
 */

public class NewsLoader {

    public static final String LOG_TAG = NewsLoader.class.getName();

    public void loadItems(String url, FeedsListener listener) {
        FeedItemsLoader feedItemsLoader = new FeedItemsLoader(url, listener);
        Thread thread = new Thread(feedItemsLoader);
        thread.start();
    }

    public interface FeedsListener {
        void onLoaded(List<Element> feeds);
    }

    static class FeedItemsLoader implements Runnable {
        Handler handler = new Handler();
        List<Element> feedItems;
        private final String url;
        private final WeakReference<FeedsListener> listener;

        public FeedItemsLoader(String url, FeedsListener listener) {
            this.url = url;
            this.listener = new WeakReference(listener);
        }

        @Override
        public void run() {
            final FeedsListener feedsListener = listener.get();

                feedItems = extractItemsFromRssUrl(url);

                if (feedsListener != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            feedsListener.onLoaded(feedItems);
                        }
                    });
                }
        }

        private List<Element> extractItemsFromRssUrl(String rssUrl) {

            List<Element> feedItems = new ArrayList<>();
            try {
                URL url = new URL(rssUrl);
                InputStream is = url.openStream();
                Document doc = new SAXBuilder().build(is);
                Element channel = doc.getRootElement().getChild("channel");
                feedItems = channel.getChildren("item");

            } catch (IOException | JDOMException e) {
                e.printStackTrace();
            }

            return feedItems;
        }
    }
}







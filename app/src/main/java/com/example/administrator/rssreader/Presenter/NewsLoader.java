package com.example.administrator.rssreader.Presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;



import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10.02.2018.
 */

public class NewsLoader {

    public static final String LOG_TAG = NewsLoader.class.getName();

    List<Element> feedItems = new ArrayList<>();
    private String rssUrl;
    Handler handler;
    Handler.Callback handlerCallback;

    NewsLoader(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    void loadItems() {
        Log.e(LOG_TAG, "loadItems()");
        FeedItemsLoader feedItemsLoader = new FeedItemsLoader();
        Thread thread = new Thread(feedItemsLoader);
        thread.start();
        handler = new Handler(handlerCallback);
        Log.e(LOG_TAG, String.valueOf(feedItems.size()));
    }

    class FeedItemsLoader implements Runnable {

        @Override
        public void run() {

            Log.e(LOG_TAG, "run");
            feedItems = extractItemsFromRssUrl(rssUrl);
            Message msg = handler.obtainMessage(0, feedItems);
            handlerCallback = new Handler.Callback() {
                public boolean handleMessage(Message msg) {
                    feedItems = (List<Element>)msg.obj;
                    return true;
                }
            };
            handler.sendMessage(msg);
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



package com.example.administrator.rssreader;

import android.os.Handler;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 26.01.2018.
 */

public class ProposedFeedItemLoader {

    public static final String LOG_TAG = ProposedFeedItemLoader.class.getName();

    public void loadProposedFeedItems(String url, ProposedFeedsListener listener){
        ProposedFeedItemsLoader proposedFeedItemsLoader = new ProposedFeedItemsLoader(url, listener);
        Thread thread = new Thread(proposedFeedItemsLoader);
        thread.start();
    }

    public interface ProposedFeedsListener {
        void onLoaded(List<ProposedFeedItem> items);
    }

    static class ProposedFeedItemsLoader implements Runnable {
        Handler handler = new Handler();
        private String baseUrl;
        private final WeakReference<ProposedFeedsListener> listener;
        List<ProposedFeedItem> proposedFeedItems;

        public ProposedFeedItemsLoader(String baseUrl, ProposedFeedsListener listener) {
            this.baseUrl = baseUrl;
            this.listener = new WeakReference(listener);
        }

        @Override
        public void run() {
            final ProposedFeedsListener proposedFeedsListener = listener.get();

            proposedFeedItems = fetchFeedItemsData(baseUrl);
            if (proposedFeedsListener != null){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        proposedFeedsListener.onLoaded(proposedFeedItems);
                    }
                });
            }
        }

        private List<ProposedFeedItem> fetchFeedItemsData(String urlText) {
            List<ProposedFeedItem> proposedFeedItemList = new ArrayList<>();

            //extract attributes via jsoup and put them in proposedFeedItemList
            try {
                Document doc = Jsoup.connect(urlText).get();
                Elements links = doc.select("a[href]");
                links.addAll(doc.select("link[href]"));

                for (Element link : links) {
                    if (link.attr("href").contains("rss")) {
                        String title = link.attr("title");
                        if (title.equals("") || title.equals("RSS")){
                            if (doc.title() != null) {
                                title = doc.title();
                            } else {
                                Elements titleLinks = doc.select("title");
                                    title = titleLinks.get(0).ownText();
                            }
                        }
                        proposedFeedItemList.add(new ProposedFeedItem(title,
                                link.attr("abs:href"),
                                extractFeedImage(doc.select("link[rel*=icon]").attr("abs:href"))));
                    }
                }
            } catch (IllegalArgumentException | IOException e) {
                e.printStackTrace();
            }
            if (proposedFeedItemList.isEmpty()) {
                Log.e(LOG_TAG, "proposedFeedItemList == null");
            }

            return proposedFeedItemList;
        }

        private Bitmap extractFeedImage(String url) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}



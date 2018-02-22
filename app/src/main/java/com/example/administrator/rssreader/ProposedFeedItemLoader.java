package com.example.administrator.rssreader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 26.01.2018.
 */

public class ProposedFeedItemLoader extends AsyncTaskLoader<List<ProposedFeedItem>> {

    public static final String LOG_TAG = ProposedFeedItemLoader.class.getName();

    private String baseUrl;

    public ProposedFeedItemLoader(String baseUrl, Context context) {
        super(context);
        this.baseUrl = baseUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ProposedFeedItem> loadInBackground() {
        return fetchFeedItemsData(baseUrl);
    }

    private List<ProposedFeedItem> fetchFeedItemsData(String urlText){
        List<ProposedFeedItem> proposedFeedItemList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(urlText).get();
            Elements links = doc.select("link");
            if (links == null || links.isEmpty()){
                links = doc.select("a");
            }
            //extract attributes via jsoup and put them in proposedFeedItemList
            for (Element link : links){
                if (link.attr("href").contains("rss") || link.attr("href").contains("feed")){
                    proposedFeedItemList.add(new ProposedFeedItem(link.attr("title"),
                            link.attr("abs:href"),
                            extractFeedImage(doc.select("link[rel*=icon]").attr("abs:href"))));
                }

            }
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
        if (proposedFeedItemList.isEmpty()){
            Log.e(LOG_TAG, "proposedFeedItemList == null");
        }

        return proposedFeedItemList;
    }

    private Bitmap extractFeedImage(String url){
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}



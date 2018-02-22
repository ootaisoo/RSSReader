package com.example.administrator.rssreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;



import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 10.02.2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<Element>> {

    public static final String LOG_TAG = NewsLoader.class.getName();

    private String rssUrl;

    public NewsLoader(String rssUrl, Context context) {
        super(context);
        this.rssUrl = rssUrl;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Element> loadInBackground() {
        Log.e(LOG_TAG, "loadInBackground()");
        return extractItemsFromRssUrl(rssUrl);
    }

    private List<Element> extractItemsFromRssUrl(String rssUrl){
//        List<NewsItem> newsItemList = new ArrayList<>();
        List<Element> feedItems = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);

//            Rome library features
//            SyndFeed feed = new SyndFeedInput().build(new XmlReader(url));
//            List<SyndEntry> items = feed.getEntries();
//            String feedLink = feed.getLink();
            InputStream is = url.openStream();
            Document doc = new SAXBuilder().build(is);
            Element channel = doc.getRootElement().getChild("channel");
            feedItems = channel.getChildren("item");
//            String feedLink = channel.getChild("link").getValue();
//
//            DateFormat sdfToDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZZ", Locale.ENGLISH);
//            DateFormat sdfToString = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
//
//            //move this logic to adapter
//            for (int i = 0; i < feeditems.size(); i++) {
//                Element item = feeditems.get(i);
//                String newsPublishingDate = null;
//                Date pubDate;
//                try {
//                    pubDate = sdfToDate.parse(item.getChild("pubDate").getValue());
//                    newsPublishingDate = sdfToString.format(pubDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                String imageUrl;
//                Bitmap newsBitmap;
//                String description;
//                Element element = feeditems.get(i);
//                if (element.getChild("enclosure") != null) {
//                    imageUrl = element.getChild("enclosure").getAttributeValue("url");
//                } else if (element.getChild("image") != null) {
//                    imageUrl = element.getChild("image").getAttributeValue("url");
//                } else if (element.getChild("media:content") != null) {
//                    imageUrl = element.getChild("media:content").getAttributeValue("url");
//                } else {
//                    imageUrl = null;
//                }
//
//                newsBitmap = extractFeedImage(imageUrl);
//                if (item.getChild("description") != null) {
//                    description = item.getChild("description").getValue();
//                } else  {
//                    description = null;
//                }
//                NewsItem newsItem = new NewsItem(item.getChild("title").getValue(),
//                        description,
//                        newsPublishingDate,
//                        feedLink, newsBitmap);
//                newsItemList.add(newsItem);
//                Log.e(LOG_TAG, String.valueOf(newsItemList.size()));
//
//            }
        } catch (IOException | JDOMException e ) {
            e.printStackTrace();
        }
        Log.e(LOG_TAG, "12");
//        return newsItemList;
        return feedItems;
    }
//      moved to adapter
//    private Bitmap extractFeedImage(String url){
//        if (url != null) {
//            if (url.startsWith("http://")) {
//                url = url.replace("http://", "https://");
//            }
//            try {
//                URL urlConnection = new URL(url);
//                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                InputStream input = connection.getInputStream();
//                return BitmapFactory.decodeStream(input);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//        return null;
//    }
}



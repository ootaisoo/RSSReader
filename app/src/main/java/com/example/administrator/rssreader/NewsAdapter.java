package com.example.administrator.rssreader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.acl.LastOwnerException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 23.01.2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public static final String LOG_TAG = NewsAdapter.class.getName();

    private List<Element> feedItems;
    private Context context;

    public NewsAdapter(List<Element> feedItems, Context context) {
        this.feedItems = feedItems;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        DateFormat sdfToDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZZ", Locale.ENGLISH);
        DateFormat sdfToString = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        Element feedItem = feedItems.get(position);

        String newsPublishingDate = null;
        Date pubDate;
        try {
            pubDate = sdfToDate.parse(feedItem.getChild("pubDate").getValue());
            newsPublishingDate = sdfToString.format(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String imageUrl;
        Bitmap newsBitmap;
        String description;

        if (feedItem.getChild("enclosure") != null) {
            imageUrl = feedItem.getChild("enclosure").getAttributeValue("url");
        } else if (feedItem.getChild("image") != null) {
            imageUrl = feedItem.getChild("image").getAttributeValue("url");
        } else if (feedItem.getChild("media:content") != null) {
            imageUrl = feedItem.getChild("media:content").getAttributeValue("url");
        } else {
            imageUrl = null;
        }

//        newsBitmap = extractFeedImage(imageUrl);
        if (feedItem.getChild("description") != null) {
            description = feedItem.getChild("description").getValue();
        } else {
            description = null;
        }

        NewsItem newsItem = new NewsItem(feedItem.getChild("title").getValue(),
                description,
                newsPublishingDate,
                feedItem.getChild("link").getValue());

//        holder.image.setImageBitmap(newsItem.getImage());
        holder.title.setText(newsItem.getTitle());
        holder.resource.setText(newsItem.getUrl());
        holder.publishingDate.setText(String.valueOf(newsItem.getTime()));
        holder.description.setText(newsItem.getDescription());
        holder.description.setVisibility(View.INVISIBLE);
        Glide
                .with(context)
                .load(imageUrl)
                .override(350, 350)
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_holder, parent, false);
        return new NewsViewHolder(view);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView resource;
        private TextView publishingDate;
        private TextView description;

        public NewsViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            resource = itemView.findViewById(R.id.resource);
            publishingDate = itemView.findViewById(R.id.publishing_date);
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    byte[] newsImageBytes = new byte[0];

//                    remove try/catch block when the image problem is resolved
                    try {
                        Bitmap newsBitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        newsBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        newsImageBytes = bos.toByteArray();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent newsActivityIntent = new Intent(context, NewsActivity.class);
                    newsActivityIntent.putExtra("newsTitle", title.getText());
                    newsActivityIntent.putExtra("newsResource", resource.getText());
                    newsActivityIntent.putExtra("newsPublishingDate", publishingDate.getText());
                    newsActivityIntent.putExtra("newsImage", newsImageBytes);
                    newsActivityIntent.putExtra("newsDescription", description.getText());

                    context.startActivity(newsActivityIntent);
                }
            });
        }
    }

//    throws NetworkOnMainThreadException

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

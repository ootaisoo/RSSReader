package com.example.administrator.rssreader.Presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.administrator.rssreader.R;
import com.example.administrator.rssreader.View.NewsActivity;

import org.jdom2.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public void clear() {
        final int size = feedItems.size();
        feedItems.clear();
        notifyItemRangeRemoved(0, size);
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

        String feedUrl;
        String imageUrl;
        String description;
        String title = feedItem.getChild("title").getValue();
        feedUrl = feedItem.getChild("link").getValue();
        String[] strings = feedUrl.split("/");
        String siteUrl = strings[2];

        if (feedItem.getChild("enclosure") != null) {
            imageUrl = feedItem.getChild("enclosure").getAttributeValue("url");
        } else if (feedItem.getChild("image") != null) {
            imageUrl = feedItem.getChild("image").getAttributeValue("url");
        } else if (feedItem.getChild("media:content") != null) {
            imageUrl = feedItem.getChild("media:content").getAttributeValue("url");
        } else {
            imageUrl = null;
        }

        if (feedItem.getChild("description") != null) {
            description = feedItem.getChild("description").getValue();
        } else {
            description = null;
        }

        NewsItem newsItem = new NewsItem(title,
                description,
                newsPublishingDate,
                siteUrl);

        holder.title.setText(newsItem.getTitle());
        holder.resource.setText(newsItem.getUrl());
        holder.feedUrl = feedUrl;
        holder.publishingDate.setText(String.valueOf(newsItem.getTime()));
        holder.description.setText(newsItem.getDescription());
        holder.description.setVisibility(View.INVISIBLE);
        holder.imageURL = imageUrl;
        if (imageUrl != null) {
            Glide
                    .with(context)
                    .load(imageUrl)
                    .into(holder.image);
        } else {
            holder.image.setVisibility(View.GONE);
        }
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
        String feedUrl;
        String imageURL;

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

                            Intent newsActivityIntent = new Intent(context, NewsActivity.class);
                    newsActivityIntent.putExtra("newsTitle", title.getText());
                    newsActivityIntent.putExtra("newsResource", feedUrl);
                    newsActivityIntent.putExtra("newsPublishingDate", publishingDate.getText());
                    newsActivityIntent.putExtra("newsImage", imageURL);
                    newsActivityIntent.putExtra("newsDescription", description.getText());

                    context.startActivity(newsActivityIntent);
                }
            });
        }
    }
}

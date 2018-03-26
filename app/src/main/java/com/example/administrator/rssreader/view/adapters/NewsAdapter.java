package com.example.administrator.rssreader.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.administrator.rssreader.NewsItem;
import com.example.administrator.rssreader.NewsItemList;
import com.example.administrator.rssreader.R;
import com.example.administrator.rssreader.view.activities.NewsActivity;
import com.example.administrator.rssreader.view.utils.Utils;

import org.jdom2.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public static final String LOG_TAG = NewsAdapter.class.getName();

    private List<NewsItem> feedItems;
    private Context context;

    public NewsAdapter(List<NewsItem> feedItems, Context context) {
        this.feedItems = feedItems;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public void clear() {
        feedItems.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<NewsItem> list) {
        feedItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsItem newsItem = feedItems.get(position);

        String feedUrl = newsItem.getUrl();
        String[] strings = feedUrl.split("/");
        String siteUrl = strings[2];

        holder.title.setText(newsItem.getTitle());
        holder.resource.setText(siteUrl);
        holder.feedUrl = feedUrl;
        holder.publishingDate.setText(String.valueOf(newsItem.getTime()));
        holder.description.setText(newsItem.getDescription());
        holder.description.setVisibility(View.INVISIBLE);
        holder.imageURL = newsItem.getImageUrl();
        if (newsItem.getImageUrl() != null) {
            Utils.setImageFromUrl(context, holder.image, newsItem.getImageUrl());
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

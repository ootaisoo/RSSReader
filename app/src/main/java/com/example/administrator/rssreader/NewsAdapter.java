package com.example.administrator.rssreader;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 23.01.2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;
    private Context context;

    public NewsAdapter(List<NewsItem> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        NewsItem newsItem = newsList.get(0);
//        holder.image.setImageURI();
        holder.title.setText(newsItem.getTitle());
        holder.resource.setText(newsItem.getUrl());
        holder.publishingDate.setText(String.valueOf(newsItem.getTime()));
        holder.description.setText(newsItem.getDescription());
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
        private  TextView publishingDate;
        private TextView description;

        public NewsViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            resource = itemView.findViewById(R.id.resource);
            publishingDate = itemView.findViewById(R.id.publishing_date);
            description = itemView.findViewById(R.id.description);


        }

    }
}

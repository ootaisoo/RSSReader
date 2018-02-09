package com.example.administrator.rssreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.rssreader.db.FeedDbHelper;
import com.example.administrator.rssreader.db.FeedsContract.FeedEntries;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.example.administrator.rssreader.db.FeedsContract.FeedEntries.URI;

/**
 * Created by Administrator on 25.01.2018.
 */

public class ProposedFeedsUdapter extends RecyclerView.Adapter<ProposedFeedsUdapter.ProposedFeedsViewHolder> {

    public static final String LOG_TAG = ProposedFeedsUdapter.class.getName();

    private List<ProposedFeedItem> proposedFeedsList;
    private Context context;

    public ProposedFeedsUdapter(List<ProposedFeedItem> proposedFeedsList, Context context) {
        this.proposedFeedsList = proposedFeedsList;
        this.context = context;
    }

    @Override
    public ProposedFeedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_feed_item, parent, false);
        return new ProposedFeedsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProposedFeedsViewHolder holder, int position) {
        ProposedFeedItem proposedFeedItem = proposedFeedsList.get(position);
        holder.feedResourceName.setText(proposedFeedItem.getFeedName());
        holder.feedUrl.setText(proposedFeedItem.getFeedUrl());
        holder.feedImage.setImageBitmap(proposedFeedItem.getFeedImage());
    }

    @Override
    public int getItemCount() {
        return proposedFeedsList.size();
    }

    public class ProposedFeedsViewHolder extends RecyclerView.ViewHolder {
        private TextView feedResourceName;
        private TextView feedUrl;
        private ImageView feedImage;

        public ProposedFeedsViewHolder(View itemView) {
            super(itemView);
            feedResourceName = itemView.findViewById(R.id.feed_resource_name);
            feedUrl = itemView.findViewById(R.id.feed_url);
            feedImage = itemView.findViewById(R.id.feed_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bitmap feedBitmap = ((BitmapDrawable)feedImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    feedBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] feedImageBytes = bos.toByteArray();
                    if (feedImageBytes.length != 0) {
                        Log.e(LOG_TAG, "bytearray isn't empty");
                    }

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(FeedEntries.FEED_NAME, String.valueOf(feedResourceName.getText()));
                    contentValues.put(FeedEntries.FEED_RESOURCE_IMAGE, feedImageBytes);

                    FeedDbHelper feedDbHelper = new FeedDbHelper(ProposedFeedsUdapter.this.context);
                    SQLiteDatabase feedsDataBase = feedDbHelper.getWritableDatabase();
                    try {
                        feedsDataBase.insertOrThrow(FeedEntries.TABLE_NAME, null, contentValues);
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                    }
                    context.getContentResolver().notifyChange(URI, null);
                    Log.e(LOG_TAG, "the numbers of rows in the cursor is " + String.valueOf(feedsDataBase.query(FeedEntries.TABLE_NAME, null, null, null, null, null, null).getCount()));
                }
            });
        }
    }
}

package com.example.administrator.rssreader.view.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rssreader.ProposedFeedItem;
import com.example.administrator.rssreader.R;
import com.example.administrator.rssreader.model.FeedDbHelper;
import com.example.administrator.rssreader.model.FeedsContract.FeedEntries;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.example.administrator.rssreader.model.FeedsContract.FeedEntries.URI;

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

    public void clear() {
        final int size = proposedFeedsList.size();
        proposedFeedsList.clear();
        notifyItemRangeRemoved(0, size);
    }

    class ProposedFeedsViewHolder extends RecyclerView.ViewHolder {
        private TextView feedResourceName;
        private TextView feedUrl;
        private ImageView feedImage;

        ProposedFeedsViewHolder(View itemView) {
            super(itemView);
            feedResourceName = itemView.findViewById(R.id.feed_resource_name);
            feedUrl = itemView.findViewById(R.id.feed_url);
            feedImage = itemView.findViewById(R.id.feed_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // adds feed info to database
                    Bitmap feedBitmap = ((BitmapDrawable)feedImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    feedBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] feedImageBytes = bos.toByteArray();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(FeedEntries.FEED_NAME, String.valueOf(feedResourceName.getText()));
                    contentValues.put(FeedEntries.FEED_RESOURCE_IMAGE, feedImageBytes);
                    contentValues.put(FeedEntries.FEED_URL, String.valueOf(feedUrl.getText()));

                    FeedDbHelper feedDbHelper = new FeedDbHelper(ProposedFeedsUdapter.this.context);
                    SQLiteDatabase feedsDataBase = feedDbHelper.getWritableDatabase();
                    try {
                        feedsDataBase.insertOrThrow(FeedEntries.TABLE_NAME, null, contentValues);
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                    }
                    context.getContentResolver().notifyChange(URI, null);
                    Toast.makeText(context, R.string.feed_added, Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "the numbers of rows in the cursor is " + String.valueOf(feedsDataBase.query(FeedEntries.TABLE_NAME, null, null, null, null, null, null).getCount()));
                }
            });
        }
    }
}

package com.example.administrator.rssreader;

        import android.app.Activity;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.administrator.rssreader.model.FeedDbHelper;

        import static com.example.administrator.rssreader.model.FeedsContract.FeedEntries.*;

/**
 * Created by Administrator on 25.01.2018.
 */

public class FeedsAdapter extends CursorRecyclerViewAdapter<FeedsAdapter.FeedsViewHolder> {

    private Context context;

    public static final String LOG_TAG = FeedsAdapter.class.getName();

    public OnFeedItemSelectedListener onFeedItemSelectedListener;

    public interface OnFeedItemSelectedListener {
        public void onFeedItemSelected(String feedUrl);
    }

    public FeedsAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    @Override
    public FeedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_holder, parent, false);
        FeedsViewHolder holder = new FeedsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedsViewHolder holder, Cursor cursor) {
        FeedItem feedItem = FeedItem.fromCursor(cursor);
        holder.feedName.setText(feedItem.getName());
        holder.image.setImageBitmap(feedItem.getImage());
        holder.feedUrl = feedItem.getFeedUrl();
    }

    public class FeedsViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView feedName;
        private String feedUrl;


        public FeedsViewHolder(final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.feed_image);
            feedName = itemView.findViewById(R.id.feed_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFeedItemSelectedListener.onFeedItemSelected(feedUrl);

                    DrawerLayout drawerLayout = ((Activity)context).findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FeedsAdapter.this.getmContext());
                    builder.setItems(R.array.feed_dialog_menu_items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0:
                                    FeedDbHelper dbHelper = new FeedDbHelper(FeedsAdapter.this.getmContext());
                                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                                    String[] selectionArgs = {String.valueOf(feedName.getText())};
                                    database.delete(TABLE_NAME, FEED_NAME + "=?", selectionArgs);
                                    context.getContentResolver().notifyChange(URI, null);
                                    break;
                            }
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                }
            });
        }
    }
}
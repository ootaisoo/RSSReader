package com.example.administrator.rssreader;

        import android.app.Activity;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.CursorAdapter;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.administrator.rssreader.db.FeedDbHelper;
        import com.example.administrator.rssreader.db.FeedsContract.FeedEntries;

        import org.w3c.dom.Text;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Observable;
        import java.util.Observer;

        import static com.example.administrator.rssreader.db.FeedsContract.FeedEntries.*;

/**
 * Created by Administrator on 25.01.2018.
 */

public class FeedsAdapter extends CursorRecyclerViewAdapter<FeedsAdapter.FeedsViewHolder> {

    private Context context;

    public static final String LOG_TAG = FeedsAdapter.class.getName();

    public FeedsAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
        Log.e(LOG_TAG, "FeedsAdapter constructor");
    }

    @Override
    public FeedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_holder, parent, false);
        FeedsViewHolder holder = new FeedsViewHolder(view);
        Log.e(LOG_TAG, "onCreateViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedsViewHolder holder, Cursor cursor) {
        FeedItem feedItem = FeedItem.fromCursor(cursor);
        holder.feedName.setText(feedItem.getName());
        holder.image.setImageBitmap(feedItem.getImage());
        Log.e(LOG_TAG, "onBindViewHolder");
    }

    public class FeedsViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView feedName;


        public FeedsViewHolder(final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.feed_image);
            feedName = itemView.findViewById(R.id.feed_name);
            Log.e(LOG_TAG, "FeedsViewHolder constructor");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

                    Fragment mainFragment = fragmentManager.findFragmentById(R.id.main_fragment);
                    Fragment drawerFragment = fragmentManager.findFragmentById(R.id.drawer_fragment);

                    Log.e(LOG_TAG, "OnClick");
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Log.e(LOG_TAG, "beginTransaction()");
                    transaction.replace(R.id.drawer_fragment, mainFragment);
                    Log.e(LOG_TAG, "transaction.replace");
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    Log.e(LOG_TAG, "transaction.setTransition");
                    transaction.addToBackStack(null);
                    Log.e(LOG_TAG, "transaction.addToBackStack");
                    transaction.commit();
                    Log.e(LOG_TAG, "transaction.commit()");
                    fragmentManager.executePendingTransactions();
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
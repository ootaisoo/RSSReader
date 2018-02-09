package com.example.administrator.rssreader;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.rssreader.db.FeedDbHelper;
import com.example.administrator.rssreader.db.FeedsContract.FeedEntries;
import com.example.administrator.rssreader.db.FeedsLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 24.01.2018.
 */

public class DrawerFragment extends Fragment implements LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = DrawerFragment.class.getName();

    public static final int URL_LOADER = 0;

    RecyclerView drawerRecyclerView;
    FeedsAdapter feedsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View drawerFragmentView = inflater.inflate(R.layout.drawer_fragment, container, false);

        drawerRecyclerView = drawerFragmentView.findViewById(R.id.drawer_recycler);
        drawerRecyclerView.setHasFixedSize(true);
        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        feedsAdapter = new FeedsAdapter(getActivity(), null);
        drawerRecyclerView.setAdapter(feedsAdapter);
        Log.e(LOG_TAG, "Feedadapter been set.");

        Button addFeedButton = drawerFragmentView.findViewById(R.id.button);
        addFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFeedIntent = new Intent(getActivity(), addFeedActivity.class);
                startActivity(addFeedIntent);
            }
        });
        getLoaderManager().initLoader(URL_LOADER, null, this);

        return drawerFragmentView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        switch (loaderID){
            case URL_LOADER:
                return new FeedsLoader(getActivity());
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null){
            Log.e(LOG_TAG, "THE CURSOR IS NULL");
        }
        feedsAdapter.swapCursor(cursor);
        Log.e(LOG_TAG, "onLoadFinished()");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        feedsAdapter.swapCursor(null);
    }
}

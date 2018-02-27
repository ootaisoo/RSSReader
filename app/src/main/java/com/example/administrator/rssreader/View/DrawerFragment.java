package com.example.administrator.rssreader.View;

import android.content.Context;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.administrator.rssreader.Presenter.DrawerFragmentPresenter;
import com.example.administrator.rssreader.Presenter.FeedsAdapter;
import com.example.administrator.rssreader.Model.FeedsLoader;
import com.example.administrator.rssreader.R;

/**
 * Created by Administrator on 24.01.2018.
 */

public class DrawerFragment extends Fragment implements LoaderCallbacks<Cursor>, DrawerView {

    public static final String LOG_TAG = DrawerFragment.class.getName();

    public static final int URL_LOADER = 0;

    RecyclerView drawerRecyclerView;
    FeedsAdapter feedsAdapter;
    DrawerFragmentPresenter drawerFragmentPresenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        feedsAdapter = new FeedsAdapter(getActivity(), null);
        try {
            if (context instanceof MainActivity) {
                feedsAdapter.onFeedItemSelectedListener = (FeedsAdapter.OnFeedItemSelectedListener) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFeedItemSelectedListener");
        }
    }
//    is necessary?
    @Override
    public void onDetach() {
        super.onDetach();
        feedsAdapter.onFeedItemSelectedListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View drawerFragmentView = inflater.inflate(R.layout.drawer_fragment, container, false);

        drawerFragmentPresenter = new DrawerFragmentPresenter(this);

        drawerRecyclerView = drawerFragmentView.findViewById(R.id.drawer_recycler);
        drawerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        drawerRecyclerView.setLayoutManager(layoutManager);

        drawerRecyclerView.setAdapter(feedsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        drawerRecyclerView.addItemDecoration(dividerItemDecoration);

        ImageButton addFeedButton = drawerFragmentView.findViewById(R.id.button);
        addFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerFragmentPresenter.callAddFeedActivity();
            }
        });
        drawerFragmentPresenter.initCursorLoader();

        return drawerFragmentView;
    }

    @Override
    public void callAddFeedActivity() {
        Intent addFeedIntent = new Intent(getActivity(), AddFeedActivity.class);
        startActivity(addFeedIntent);
    }

    @Override
    public void initCursorLoader() {
        getLoaderManager().initLoader(URL_LOADER, null, this);
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
        }
        feedsAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        feedsAdapter.swapCursor(null);
    }
}

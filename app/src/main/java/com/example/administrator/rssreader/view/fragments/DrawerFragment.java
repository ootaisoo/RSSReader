package com.example.administrator.rssreader.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.administrator.rssreader.R;
import com.example.administrator.rssreader.presenter.DrawerFragmentPresenter;
import com.example.administrator.rssreader.view.DrawerView;
import com.example.administrator.rssreader.view.activities.AddFeedActivity;
import com.example.administrator.rssreader.view.activities.MainActivity;
import com.example.administrator.rssreader.view.adapters.FeedsAdapter;

public class DrawerFragment extends BaseFragment<DrawerFragmentPresenter> implements DrawerView {

    public static final String LOG_TAG = DrawerFragment.class.getName();

    RecyclerView drawerRecyclerView;
    FeedsAdapter feedsAdapter;

    @Override
    protected void inject() {
            setPresenter(new DrawerFragmentPresenter(this));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        feedsAdapter.onFeedItemSelectedListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View drawerFragmentView = inflater.inflate(R.layout.drawer_fragment, container, false);

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
                Intent addFeedIntent = new Intent(getActivity(), AddFeedActivity.class);
                startActivity(addFeedIntent);
            }
        });
        getPresenter().loadFromDb();

        return drawerFragmentView;
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void onCursorLoaded(Cursor cursor) {
        feedsAdapter.swapCursor(cursor);
    }
}

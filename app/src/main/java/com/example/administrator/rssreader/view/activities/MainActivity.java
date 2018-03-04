package com.example.administrator.rssreader.view.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.administrator.rssreader.R;
import com.example.administrator.rssreader.view.adapters.FeedsAdapter;
import com.example.administrator.rssreader.view.fragments.MainFragment;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity implements FeedsAdapter.OnFeedItemSelectedListener {

    public static final String LOG_TAG = MainActivity.class.getName();

    private ActionBarDrawerToggle drawerToggle;

    @Override
    public void onFeedItemSelected(String feedUrl) {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        Bundle args = new Bundle();
        args.putString("rssUrl", feedUrl);
        mainFragment.setArguments(args);
        mainFragment.loadNewData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        if (drawerLayout != null) {
            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }

        if (getResources().getConfiguration().orientation != ORIENTATION_LANDSCAPE) {
            Log.e(LOG_TAG, "A");

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

        @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}

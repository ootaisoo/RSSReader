package com.example.administrator.rssreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 24.01.2018.
 */

public class MainFragment extends Fragment {

    private List<NewsItem> newsItemList;
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainFragmentView = inflater.inflate(R.layout.main_fragment, container, false);

        RecyclerView mainRecyclerView = mainFragmentView.findViewById(R.id.main_recycler);
        mainRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
        mainRecyclerView.setLayoutManager(llm2);

        //test
        NewsItem newsItemExample = new NewsItem("c", "r", "t","e");
        newsItemList = new ArrayList();
        newsItemList.add(newsItemExample);

        newsAdapter = new NewsAdapter(newsItemList, getActivity());
        mainRecyclerView.setAdapter(newsAdapter);

        return mainFragmentView;
    }
}

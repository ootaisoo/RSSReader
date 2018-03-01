package com.example.administrator.rssreader.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.rssreader.view.MainView;
import com.example.administrator.rssreader.view.MvpView;

/**
 * Created by Administrator on 28.02.2018.
 */

public abstract class BasePresenter<V extends MvpView> {

    private final V view;

    public BasePresenter(V view) {
        this.view = view;
    }

    protected V getView() {
        return view;
    }

    public void onCreate(@Nullable Bundle savedInstanceState){}
    public void onViewCreated(){}
    public void onStart(){}
    public void onResume(){}
    public void onPause(){}
    public void onStop(){}
    public void onDestroy(){}
}

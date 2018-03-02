package com.example.administrator.rssreader.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.administrator.rssreader.presenter.BasePresenter;

/**
 * Created by Administrator on 28.02.2018.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    private P presenter;

    protected P getPresenter() {
        return presenter;
    }

    protected void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public void onCreate(Bundle savedInstanceState){
        inject();
        super.onCreate(savedInstanceState);
        this.presenter.onCreate(savedInstanceState);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreated();
    }

    public void onStart(){
        super.onStart();
        presenter.onStart();
    }

    public void onResume(){
        super.onResume();
        presenter.onResume();
    }

    public void onPause(){
        super.onPause();
        presenter.onPause();
    }

    public void onStop(){
        super.onStop();
        presenter.onStop();
    }

    public void onDestroy(){
        super.onDestroy();
        presenter.onDestroy();
    }

    protected abstract void inject();
}

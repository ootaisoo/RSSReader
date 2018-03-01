package com.example.administrator.rssreader.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.rssreader.presenter.BasePresenter;

/**
 * Created by Administrator on 28.02.2018.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    private P presenter;

    protected P getPresenter() {
        return presenter;
    }

    protected void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState){
        inject();
        super.onCreate(savedInstanceState);
        presenter.onCreate(savedInstanceState);
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

package com.news.today.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.news.today.mvp.MvpControler;
import com.news.today.mvp.lf.view.IBindView;
import com.news.today.mvp.lf.IGroup;
import com.news.today.mvp.lf.view.IMvpView;

/**
 * Created by anson on 2018/4/5.
 */

public abstract class BaseMvpActivity extends AppCompatActivity implements IMvpView,IBindView ,IGroup{
    private MvpControler mvpControler;

    public BaseMvpActivity() {
    }

    public final MvpControler getMvpControler() {
        if(this.mvpControler == null) {
            synchronized(this) {
                this.mvpControler = MvpControler.newInstance();
            }
        }
        return this.mvpControler;
    }

    @CallSuper
    protected void onNewIntent(Intent intent) {
        MvpControler connector = this.getMvpControler();
        if(connector != null) {
            connector.onNewIntent(intent);
        }
        super.onNewIntent(intent);
    }

    @CallSuper
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        MvpControler connector = this.getMvpControler();
        if(connector != null) {
            connector.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onStart() {
        MvpControler connector = this.getMvpControler();
        if(connector != null) {
            connector.onStart();
        }
        super.onStart();
    }

    protected void onResume() {
        MvpControler connector = this.getMvpControler();
        if(connector != null) {
            connector.onResume(this);
        }
        super.onResume();
    }

    protected void onPause() {
        MvpControler connector = this.getMvpControler();
        if(connector != null) {
            connector.onPause();
        }
        super.onPause();
    }

    protected void onStop() {
        MvpControler connector = this.getMvpControler();
        if(connector != null) {
            connector.onStop();
        }
        super.onStop();
    }

    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        MvpControler connector = this.getMvpControler();
        if(connector != null) {
            connector.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initBeforeCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        try {
            this.initAfterCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle params = this.getIntent().getExtras();
        if(params == null) {
            params = new Bundle();
        }
        MvpControler connector = this.getMvpControler();
        if(connector != null) {
            connector.onCreate(savedInstanceState, params);
            connector.onViewCreate(savedInstanceState, params);
        }

    }

    protected void onDestroy() {
        MvpControler connector = this.getMvpControler();
        if(connector != null) {
            connector.onViewDestroy();
            connector.onDestroy();
        }
        super.onDestroy();
    }

    protected void initBeforeCreate(Bundle savedInstanceState) {
    }

    protected void initAfterCreate(Bundle savedInstanceState) {
    }

}

package com.news.today.mvp;

import android.content.Intent;
import android.os.Bundle;

import com.news.today.mvp.lf.presenter.ILifeCyclePresenter;
import com.news.today.mvp.lf.view.IMvpView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by anson on 2018/4/5.
 */

public class MvpControler {

    private Set<ILifeCyclePresenter> lifeCycleSet = new HashSet();

    private MvpControler() {
    }

    public static MvpControler newInstance() {
        return new MvpControler();
    }

    public synchronized void savePresenter(ILifeCyclePresenter presenter) {
        this.lifeCycleSet.add(presenter);
    }

    public synchronized void onCreate(Bundle savedInstanceState, Bundle extras) {
        Iterator var3 = this.lifeCycleSet.iterator();

        while (var3.hasNext()) {
            ILifeCyclePresenter presenter = (ILifeCyclePresenter) var3.next();
            if (extras == null) {
                extras = new Bundle();
            }

            presenter.initParam(extras);
            presenter.onCreate(savedInstanceState, extras);
        }

    }

    public synchronized void onDestroy() {
        Iterator var1 = this.lifeCycleSet.iterator();

        while (var1.hasNext()) {
            ILifeCyclePresenter presenter = (ILifeCyclePresenter) var1.next();
            presenter.onDestroy();
        }
        this.lifeCycleSet.clear();
    }

    public synchronized void onViewCreate(Bundle savedInstanceState, Bundle extras) {
        Iterator var3 = this.lifeCycleSet.iterator();
        while (var3.hasNext()) {
            ILifeCyclePresenter presenter = (ILifeCyclePresenter) var3.next();
            if (extras == null) {
                extras = new Bundle();
            }

            presenter.initParam(extras);
            presenter.onViewCreate(savedInstanceState, extras);
        }

    }

    public synchronized void onViewDestroy() {
        Iterator var1 = this.lifeCycleSet.iterator();
        while (var1.hasNext()) {
            ILifeCyclePresenter presenter = (ILifeCyclePresenter) var1.next();
            presenter.onViewDestroy();
        }
    }

    public synchronized void onResume(IMvpView mvpView) {
        Iterator var2 = this.lifeCycleSet.iterator();

        while (var2.hasNext()) {
            ILifeCyclePresenter presenter1 = (ILifeCyclePresenter) var2.next();
            presenter1.onResume();
        }

    }

    public synchronized void onPause() {
        Iterator var1 = this.lifeCycleSet.iterator();

        while (var1.hasNext()) {
            ILifeCyclePresenter presenter = (ILifeCyclePresenter) var1.next();
            presenter.onPause();
        }

    }

    public synchronized void onSaveInstanceState(Bundle outState) {
        Iterator var2 = this.lifeCycleSet.iterator();

        while (var2.hasNext()) {
            ILifeCyclePresenter presenter = (ILifeCyclePresenter) var2.next();
            presenter.onSaveInstanceState(outState);
        }

    }

    public synchronized void onActivityResult(int requestCode, int resultCode, Intent data) {
        Iterator var4 = this.lifeCycleSet.iterator();

        while (var4.hasNext()) {
            ILifeCyclePresenter presenter = (ILifeCyclePresenter) var4.next();
            presenter.onActivityResult(requestCode, resultCode, data);
        }

    }

    public synchronized void onStart() {
        Iterator var1 = this.lifeCycleSet.iterator();

        while (var1.hasNext()) {
            ILifeCyclePresenter presenter = (ILifeCyclePresenter) var1.next();
            presenter.onStart();
        }

    }

    public synchronized void onStop() {
        Iterator var1 = this.lifeCycleSet.iterator();

        while (var1.hasNext()) {
            ILifeCyclePresenter presenter = (ILifeCyclePresenter) var1.next();
            presenter.onStop();
        }

    }

    public synchronized void onNewIntent(Intent intent) {
        Iterator var2 = this.lifeCycleSet.iterator();
        while (var2.hasNext()) {
            ILifeCyclePresenter presenter = (ILifeCyclePresenter) var2.next();
            presenter.onNewIntent(intent);
        }

    }
}

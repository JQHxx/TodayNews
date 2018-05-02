package com.news.today.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.news.today.mvp.MvpControler;
import com.news.today.mvp.lf.presenter.ILifeCyclePresenter;
import com.news.today.mvp.lf.view.IMvpView;

import java.lang.ref.SoftReference;

/**
 * Created by anson on 2018/4/6.
 */

public abstract class BaseMvpPresenter<T extends IMvpView> implements ILifeCyclePresenter {
    protected SoftReference<T> softReferenceView;
    protected Context          mContext;
    protected IMvpView mView;
    protected BaseMvpPresenter() {
        super();
    }

    public BaseMvpPresenter(IMvpView iView) {
        super();
        try {
            takeView(iView);
            MvpControler connector = iView.getMvpControler();
            connector.savePresenter(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mView = iView;
        this.initContext();
    }
    private void initContext() {
        if(this.mView instanceof Fragment) {
            this.mContext = ((Fragment)this.mView).getActivity();
        } else {
            this.mContext = (Activity)this.mView;
        }

    }
    @Override
    public synchronized void takeView(IMvpView mvpView) {
        if (softReferenceView == null) {
            softReferenceView = new SoftReference(mvpView);
        } else {
            T view = softReferenceView.get();
            if (view != mvpView) {
                softReferenceView = new SoftReference(mvpView);
            }
        }
    }

    @Override
    public synchronized void destroyView() {
        softReferenceView = null;
    }

    @NonNull
    protected T getView() {
        T view = softReferenceView != null ? softReferenceView.get() : null;
        if (view == null) {
            return getEmptyView();
        }
        return view;
    }


    protected abstract T getEmptyView();

}

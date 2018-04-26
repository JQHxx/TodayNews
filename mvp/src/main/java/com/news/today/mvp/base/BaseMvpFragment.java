package com.news.today.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.news.today.mvp.MvpControler;
import com.news.today.mvp.lf.view.IBindView;
import com.news.today.mvp.lf.view.IMvpFragment;
import com.news.today.mvp.lf.view.IMvpView;

/**
 * Created by anson on 2018/4/25.
 */

public abstract class BaseMvpFragment extends Fragment implements IMvpFragment,IMvpView,IBindView {

    private MvpControler presenterConnector;
    private View view;

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public final MvpControler getMvpControler() {
        if (presenterConnector == null) {
            presenterConnector = MvpControler.newInstance();
        }
        return presenterConnector;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpControler().onCreate(savedInstanceState, getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            view = initView(inflater, container, savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMvpControler().onViewCreate(savedInstanceState, getArguments());
        return view;
    }

    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMvpControler().onViewDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMvpControler().onDestroy();
    }

    @CallSuper
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getMvpControler().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpControler().onSaveInstanceState(outState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpControler().onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpControler().onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        getMvpControler().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvpControler().onStop();
    }
}

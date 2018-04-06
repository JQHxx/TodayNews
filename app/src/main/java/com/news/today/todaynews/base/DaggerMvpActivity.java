package com.news.today.todaynews.base;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.news.today.mvp.base.BaseMvpActivity;
import com.news.today.todaynews.AbsApplication;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by anson on 2018/4/5.
 */

public abstract class DaggerMvpActivity extends BaseMvpActivity implements HasSupportFragmentInjector {

    //dagger 使用mvp
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    // 在onCreate 之前
    @Override
    protected void initBeforeCreate(Bundle savedInstanceState) {
        super.initBeforeCreate(savedInstanceState);
        doInject();
    }
    // 在onCreate 之后
    @Override
    protected void initAfterCreate(@Nullable Bundle savedInstanceState) {
        View rootView = null;
        int rootLayoutId = getRootLayoutId();
        if (rootLayoutId > 0) {
            rootView = inflateView(this, rootLayoutId);
            try {
                setContentView(rootView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("rootLayoutId is < 0");
        }
        if (rootView != null) {
            try {
                bindView(rootView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                afterViewBind(rootView, savedInstanceState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("rootView is null");
        }
    }

    /**
     * 依赖注入
     */
    protected void doInject() {
        try {
            AndroidInjection.inject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //绑定view
    @Override
    public void bindView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    public View inflateView(Context context, int rootLayoutId) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(rootLayoutId, null, false);
        return rootView;
    }

    @Override
    public String groupName() {
        return getClass().getName() + hashCode();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        if (dispatchingFragmentInjector == null) {
            Application app = getApplication();
            if (app instanceof AbsApplication) {
                return ((AbsApplication) app).supportFragmentInjector();
            }
        }
        return dispatchingFragmentInjector;
    }
}

package com.news.today.todaynews.edgesys.dagger;

import com.news.today.todaynews.annotation.ActivityScope;
import com.news.today.todaynews.edgesys.lf.IHttpTestContract;
import com.news.today.todaynews.edgesys.lf.ISplashContract;
import com.news.today.todaynews.edgesys.presenter.HttpTestActivityPresenter;
import com.news.today.todaynews.edgesys.view.HttpTestActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anson on 2018/4/6.
 */
@Module
public class HttpTestActivityProvide {
    @ActivityScope
    @Provides
    public static IHttpTestContract.IView provideView(HttpTestActivity activity) {
        return activity;
    }

    @ActivityScope
    @Provides
    public static IHttpTestContract.IPresenter providePresenter(HttpTestActivityPresenter presenter) {
        return presenter;
    }
}

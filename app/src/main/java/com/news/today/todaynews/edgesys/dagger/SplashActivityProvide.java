package com.news.today.todaynews.edgesys.dagger;

import com.news.today.todaynews.annotation.ActivityScope;
import com.news.today.todaynews.edgesys.lf.ISplashContract;
import com.news.today.todaynews.edgesys.presenter.SplashActivityPresenter;
import com.news.today.todaynews.edgesys.view.SplashActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anson on 2018/4/6.
 */
@Module
public class SplashActivityProvide {
    @ActivityScope
    @Provides
    public static ISplashContract.IView provideView(SplashActivity activity) {
        return activity;
    }

    @ActivityScope
    @Provides
    public static ISplashContract.IPresenter providePresenter(SplashActivityPresenter presenter) {
        return presenter;
    }
}

package com.news.today.todaynews.homesys.home.dagger;

import com.news.today.todaynews.annotation.ActivityScope;
import com.news.today.todaynews.homesys.home.view.MainActivity;
import com.news.today.todaynews.homesys.home.lf.IMainActivityContract;
import com.news.today.todaynews.homesys.home.presenter.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anson on 2018/4/6.
 */
@Module
public class MainActivityProvide {
    @ActivityScope
    @Provides
    public static IMainActivityContract.IView provideView(MainActivity activity) {
        return activity;
    }

    @ActivityScope
    @Provides
    public static IMainActivityContract.IPresenter providePresenter(MainActivityPresenter presenter) {
        return presenter;
    }
}

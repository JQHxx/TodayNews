package com.news.today.todaynews.homesys.shanghai.dagger;

import com.news.today.todaynews.annotation.ActivityScope;
import com.news.today.todaynews.annotation.FragmentScope;
import com.news.today.todaynews.homesys.shanghai.view.ShangHaiFragments;
import com.news.today.todaynews.homesys.shanghai.lf.IShangHaiContract;
import com.news.today.todaynews.homesys.shanghai.presenter.ShangHaiPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anson on 2018/4/6.
 */
@Module
public class ShangHaiFragmentsProvide {
    @FragmentScope
    @Provides
    public static IShangHaiContract.IView provideView(ShangHaiFragments activity) {
        return activity;
    }

    @FragmentScope
    @Provides
    public static IShangHaiContract.IPresenter providePresenter(ShangHaiPresenter presenter) {
        return presenter;
    }
}

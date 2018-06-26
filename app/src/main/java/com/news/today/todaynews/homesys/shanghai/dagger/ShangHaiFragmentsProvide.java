package com.news.today.todaynews.homesys.shanghai.dagger;

import com.news.today.todaynews.annotation.FragmentScope;
import com.news.today.todaynews.homesys.shanghai.lf.IShangHaiContract;
import com.news.today.todaynews.homesys.shanghai.presenter.ShangHaiPresenter;
import com.news.today.todaynews.homesys.shanghai.view.ShangHaiFragment;
import com.news.today.todaynews.homesys.video.IVideoContract;
import com.news.today.todaynews.homesys.video.presenter.VideoPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anson on 2018/4/6.
 */
@Module
public class ShangHaiFragmentsProvide {
    @FragmentScope
    @Provides
    public static IShangHaiContract.IView provideView(ShangHaiFragment activity) {
        return activity;
    }

    @FragmentScope
    @Provides
    public static IVideoContract.IView provideVideoView(ShangHaiFragment activity) {
        return activity;
    }

    @FragmentScope
    @Provides
    public static IShangHaiContract.IPresenter providePresenter(ShangHaiPresenter presenter) {
        return presenter;
    }

    @FragmentScope
    @Provides
    public static IVideoContract.IPresenter provideVideoPresenter(VideoPresenter presenter) {
        return presenter;
    }
}

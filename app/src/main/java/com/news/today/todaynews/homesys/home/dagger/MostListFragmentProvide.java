package com.news.today.todaynews.homesys.home.dagger;

import com.news.today.todaynews.annotation.FragmentScope;
import com.news.today.todaynews.edgesys.lf.IHttpTestContract;
import com.news.today.todaynews.edgesys.presenter.HttpTestActivityPresenter;
import com.news.today.todaynews.homesys.shanghai.view.MostListFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anson on 2018/4/6.
 */
@Module
public class MostListFragmentProvide {
    @FragmentScope
    @Provides
    public static IHttpTestContract.IView provideView(MostListFragment activity) {
        return activity;
    }

    @FragmentScope
    @Provides
    public static IHttpTestContract.IPresenter providePresenter(HttpTestActivityPresenter presenter) {
        return presenter;
    }
}

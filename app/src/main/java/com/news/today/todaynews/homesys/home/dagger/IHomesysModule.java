package com.news.today.todaynews.homesys.home.dagger;

import com.news.today.todaynews.annotation.ActivityScope;
import com.news.today.todaynews.homesys.home.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by anson on 2018/4/6.
 */
@Module
public abstract class IHomesysModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = {MainActivityProvide.class})
    public abstract MainActivity buildMainActivity();

}

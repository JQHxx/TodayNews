package com.news.today.todaynews.edgesys.dagger;

import com.news.today.todaynews.annotation.ActivityScope;
import com.news.today.todaynews.edgesys.view.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by anson on 2018/4/6.
 */
@Module
public abstract class IEdgesysModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = {SplashActivityProvide.class})
    public abstract SplashActivity buildSplashActivity();
}

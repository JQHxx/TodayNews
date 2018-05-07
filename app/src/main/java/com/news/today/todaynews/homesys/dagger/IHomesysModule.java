package com.news.today.todaynews.homesys.dagger;

import com.news.today.todaynews.annotation.ActivityScope;
import com.news.today.todaynews.annotation.FragmentScope;
import com.news.today.todaynews.homesys.home.dagger.MainActivityProvide;
import com.news.today.todaynews.homesys.home.dagger.MostListFragmentProvide;
import com.news.today.todaynews.homesys.home.view.MainActivity;
import com.news.today.todaynews.homesys.shanghai.dagger.ShangHaiFragmentsProvide;
import com.news.today.todaynews.homesys.shanghai.view.MostListFragment;
import com.news.today.todaynews.homesys.shanghai.view.ShangHaiFragment;

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

    @FragmentScope
    @ContributesAndroidInjector(modules = {ShangHaiFragmentsProvide.class})
    public abstract ShangHaiFragment buildShangHaiFragments();

    @FragmentScope
    @ContributesAndroidInjector(modules = {MostListFragmentProvide.class})
    public abstract MostListFragment buildMostListFragment();


}

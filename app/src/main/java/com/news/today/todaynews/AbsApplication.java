package com.news.today.todaynews;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.support.v4.app.Fragment;

import com.news.today.todaynews.helper.ContextHelper;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by anson on 2018/4/4.
 */

public abstract class AbsApplication extends Application implements HasActivityInjector
        , HasServiceInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;
    @Inject
    DispatchingAndroidInjector<Service>  dispatchingServiceInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHelper.init(this);
        doInject();
        afterInject();
    }

    protected abstract void doInject();


    protected abstract void afterInject();


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingFragmentInjector;
    }
}

package com.news.today.todaynews;

/**
 * Created by anson on 2018/4/4.
 */

public class TodayNewsApplication extends AbsApplication{

    @Override
    protected void doInject() {
        DaggerTodayNewsApplicationComponent.create().inject(this);
    }

    @Override
    protected void afterInject() {
        //其他初始化的操作

    }
}

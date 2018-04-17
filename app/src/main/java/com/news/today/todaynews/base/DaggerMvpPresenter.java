package com.news.today.todaynews.base;

import android.content.Intent;
import android.os.Bundle;

import com.news.today.mvp.base.BaseMvpPresenter;
import com.news.today.mvp.lf.view.IMvpView;
import com.news.today.task.AsyncTaskInstance;
import com.news.today.task.helper.TaskHelper;
import com.news.today.task.lf.IGroup;
import com.news.today.task.AbsTask;

/**
 * Created by anson on 2018/4/6.
 */

public abstract class DaggerMvpPresenter<T extends IMvpView> extends BaseMvpPresenter<T> implements IGroup{

    public DaggerMvpPresenter(T view) {
        super(view);
    }


    @Override
    public void initParam(Bundle var1) {

    }

    @Override
    public void onCreate(Bundle var1, Bundle var2) {

    }

    @Override
    public void onViewCreate(Bundle var1, Bundle var2) {

    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onSaveInstanceState(Bundle var1) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onNewIntent(Intent var1) {

    }

    @Override
    public void onActivityResult(int var1, int var2, Intent var3) {

    }

    @Override
    public String groupName() {
        T view = getView();
        return ((IGroup) view).groupName();
    }


    public <k,v> AsyncTaskInstance<k> submitTask(AbsTask<k,v> task) {
        return TaskHelper.submitTask(groupName(),task,task );
    }

}

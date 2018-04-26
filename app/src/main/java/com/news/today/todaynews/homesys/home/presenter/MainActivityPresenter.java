package com.news.today.todaynews.homesys.home.presenter;

import com.news.today.todaynews.base.DaggerMvpPresenter;
import com.news.today.todaynews.homesys.home.lf.IMainActivityContract;

import javax.inject.Inject;

/**
 * Created by anson on 2018/4/6.
 */

public class MainActivityPresenter extends DaggerMvpPresenter<IMainActivityContract.IView> implements IMainActivityContract.IPresenter {



    @Inject
    public MainActivityPresenter(IMainActivityContract.IView view) {
        super(view);
    }


    @Override
    protected IMainActivityContract.IView getEmptyView() {
        return IMainActivityContract.emptyView;
    }


}

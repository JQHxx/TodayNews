package com.news.today.todaynews.homesys.home.lf;

import com.news.today.mvp.MvpControler;
import com.news.today.mvp.lf.presenter.ILifeCyclePresenter;
import com.news.today.mvp.lf.view.IMvpView;

/**
 * Created by anson on 2018/4/25.
 */

public interface IMainActivityContract {

    interface IView extends IMvpView {
    }

    interface IPresenter extends ILifeCyclePresenter {
    }

    IView emptyView = new IView() {

        @Override
        public MvpControler getMvpControler() {
            return null;
        }
    };
}

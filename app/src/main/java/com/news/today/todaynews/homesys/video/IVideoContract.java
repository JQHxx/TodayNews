package com.news.today.todaynews.homesys.video;

import com.news.today.mvp.MvpControler;
import com.news.today.mvp.lf.presenter.ILifeCyclePresenter;
import com.news.today.mvp.lf.view.IMvpView;
import com.news.today.todaynews.base.DaggerMvpFragment;

import java.util.ArrayList;

/**
 * Created by anson on 2018/4/6.
 */

public interface IVideoContract {
    interface IView extends IMvpView {

    }

    interface IPresenter extends ILifeCyclePresenter {
        void handlePlayClick();
    }

    IView emptyView = new IView() {

        @Override
        public MvpControler getMvpControler() {
            return null;
        }
    };
}

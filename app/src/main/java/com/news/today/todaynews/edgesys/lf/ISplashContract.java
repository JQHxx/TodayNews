package com.news.today.todaynews.edgesys.lf;

import com.news.today.mvp.MvpControler;
import com.news.today.mvp.lf.presenter.ILifeCyclePresenter;
import com.news.today.mvp.lf.view.IMvpView;

/**
 * Created by anson on 2018/4/6.
 */

public interface ISplashContract {
    interface IView extends IMvpView {
        void showCountDownTime(String countDownTime);

        void showTimeOver();
    }

    interface IPresenter extends ILifeCyclePresenter {
        void registerNormalCountDown(int time);
    }

    IView emptyView = new IView() {
        @Override
        public void showCountDownTime(String countDownTime) {

        }

        @Override
        public void showTimeOver() {

        }

        @Override
        public MvpControler getMvpControler() {
            return null;
        }
    };
}

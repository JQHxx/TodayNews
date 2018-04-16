package com.news.today.todaynews.edgesys.lf;

import com.news.today.mvp.MvpControler;
import com.news.today.mvp.lf.presenter.ILifeCyclePresenter;
import com.news.today.mvp.lf.view.IMvpView;

/**
 * Created by anson on 2018/4/6.
 */

public interface IHttpTestContract {
    interface IView extends IMvpView {

    }

    interface IPresenter extends ILifeCyclePresenter {
        void getNetData();
    }

    IView emptyView = new IView() {

        @Override
        public MvpControler getMvpControler() {
            return null;
        }
    };
}

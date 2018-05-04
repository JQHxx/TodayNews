package com.news.today.todaynews.homesys.home.lf;

import com.news.today.mvp.MvpControler;
import com.news.today.mvp.lf.presenter.ILifeCyclePresenter;
import com.news.today.mvp.lf.view.IMvpView;
import com.news.today.todaynews.base.DaggerMvpFragment;
import com.news.today.todaynews.homesys.shanghai.view.ShangHaiFragments;

/**
 * Created by anson on 2018/4/25.
 */

public interface IMainActivityContract {

    interface IView extends IMvpView {
        void addFragment(DaggerMvpFragment baseFragment);
        void showFragment(DaggerMvpFragment baseFragment);
        void hideFragment(DaggerMvpFragment baseFragment);
    }

    interface IPresenter extends ILifeCyclePresenter {
        void initHomeFragmet();

        void replaceFragment(int index);

        int getCurrentCheckedId();

        void setCurrentCheckedId(int id);

        int getCurrentFragmentIndex();

        void setCurrentFragmentIndex(int index);

        ShangHaiFragments getShangHaiFragment();

    }

    IView emptyView = new IView() {

        @Override
        public void addFragment(DaggerMvpFragment baseFragment) {

        }

        @Override
        public void showFragment(DaggerMvpFragment baseFragment) {

        }

        @Override
        public void hideFragment(DaggerMvpFragment baseFragment) {

        }

        @Override
        public MvpControler getMvpControler() {
            return null;
        }
    };
}

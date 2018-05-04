package com.news.today.todaynews.homesys.home.presenter;

import com.news.today.todaynews.R;
import com.news.today.todaynews.base.DaggerMvpFragment;
import com.news.today.todaynews.base.DaggerMvpPresenter;
import com.news.today.todaynews.homesys.home.lf.IMainActivityContract;
import com.news.today.todaynews.homesys.view.BeiJingFragment;
import com.news.today.todaynews.homesys.view.HangZhouFragment;
import com.news.today.todaynews.homesys.shanghai.view.ShangHaiFragments;
import com.news.today.todaynews.homesys.view.ShenZhenFragment;

import javax.inject.Inject;

/**
 * Created by anson on 2018/4/6.
 */

public class MainActivityPresenter extends DaggerMvpPresenter<IMainActivityContract.IView> implements IMainActivityContract.IPresenter {

    private int currentFragmentIndex = 0;
    private DaggerMvpFragment[] mainFragments = new DaggerMvpFragment[4];
    private int currentCheckedId;

    @Inject
    public MainActivityPresenter(IMainActivityContract.IView view) {
        super(view);
    }


    @Override
    protected IMainActivityContract.IView getEmptyView() {
        return IMainActivityContract.emptyView;
    }




    @Override
    public void initHomeFragmet() {
        currentFragmentIndex = 0;
        replaceFragment(currentFragmentIndex);
    }

    //替换Fragment
    @Override
    public void replaceFragment(int index) {
        for (int i = 0; i < mainFragments.length; i++) {
            if (index != i) {
                if (mainFragments[i] != null) {
                    hideFragment(mainFragments[i]);
                }
            }
        }

        DaggerMvpFragment fragment = mainFragments[index];
        if (fragment != null) {
            addAndShowFragment(fragment);
            setCurChecked(index);
        } else {
            newFragmentAndSwitchAtIndex(index);
            setCurChecked(index);
        }
    }

    @Override
    public int getCurrentCheckedId() {
        return currentCheckedId;
    }

    @Override
    public void setCurrentCheckedId(int id) {
        currentCheckedId = id;
    }

    @Override
    public int getCurrentFragmentIndex() {
        return currentFragmentIndex;
    }

    @Override
    public void setCurrentFragmentIndex(int index) {
        currentFragmentIndex = index;
    }


    //记录当前的点击位置
    private void setCurChecked(int index) {
        switch (index) {
            case 0: {
                currentFragmentIndex = 0;
                currentCheckedId = R.id.rb_main_nav_home;
            }
            break;
            case 1: {
                currentFragmentIndex = 1;
                currentCheckedId = R.id.rb_main_nav_car_source;
            }
            break;
            case 2: {
                currentFragmentIndex = 2;
                currentCheckedId = R.id.rb_main_nav_home_beijing;
            }
            break;
            case 3: {
                currentFragmentIndex = 3;
                currentCheckedId = R.id.rb_main_nav_car_source_shenzhen;
            }
            break;
        }
    }

    private void newFragmentAndSwitchAtIndex(int index) {
        DaggerMvpFragment baseFragment = null;
        switch (index) {
            case 0:
                baseFragment = ShangHaiFragments.newInstance();
                break;
            case 1:
                baseFragment = HangZhouFragment.newInstance();
                break;
            case 2:
                baseFragment = BeiJingFragment.newInstance();
                break;
            case 3:
                baseFragment = ShenZhenFragment.newInstance();
                break;
        }
        mainFragments[index] = baseFragment;
        addAndShowFragment(baseFragment);
    }

    //展示Fragment
    private void addAndShowFragment(DaggerMvpFragment baseFragment) {
        if (!baseFragment.isAdded()) {
            getView().addFragment(baseFragment);
        } else if (!baseFragment.isVisible()) {
            getView().showFragment(baseFragment);
        }
    }

    //隐藏Fragment
    private void hideFragment(DaggerMvpFragment baseFragment) {
        if (baseFragment == null) {
            return;
        }
        if (baseFragment.isVisible()) {
            getView().hideFragment(baseFragment);
        }
    }

    @Override
    public ShangHaiFragments getShangHaiFragment() {
        if (mainFragments != null && mainFragments[0] != null) {
            return (ShangHaiFragments) mainFragments[0];
        }
        return null;
    }

}

package com.news.today.todaynews.edgesys.presenter;

import android.view.View;

import com.news.today.mvp.base.BaseMvpPresenter;
import com.news.today.mvp.lf.view.IMvpView;
import com.news.today.todaynews.R;
import com.news.today.todaynews.base.DaggerMvpPresenter;
import com.news.today.todaynews.edgesys.lf.ISplashContract;
import com.news.today.todaynews.edgesys.view.SplashActivity;
import com.news.today.todaynews.helper.ContextHelper;
import com.news.today.todaynews.homesys.MainActivity;
import com.news.today.todaynews.utils.CustomCountDownTimer;

import javax.inject.Inject;

import static com.news.today.todaynews.helper.ContextHelper.getString;

/**
 * Created by anson on 2018/4/6.
 */

public class SplashActivityPresenter extends DaggerMvpPresenter<ISplashContract.IView> implements ISplashContract.IPresenter {


    private CustomCountDownTimer mCustomCountDownTimer;

    @Inject
    public SplashActivityPresenter(ISplashContract.IView view) {
        super(view);
    }


    @Override
    public void registerNormalCountDown(int time) {
        mCustomCountDownTimer = new CustomCountDownTimer(time,
                new CustomCountDownTimer.ICountDownHandler() {
                    @Override
                    public void onTicker(int time) {
                        getView().showCountDownTime(ContextHelper.getString(R.string.str_splash_count_down, time));
                    }

                    @Override
                    public void onFinish() {
                        getView().showTimeOver();
                    }
                });
    }

    @Override
    protected ISplashContract.IView getEmptyView() {
        return ISplashContract.emptyView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mCustomCountDownTimer) {
            mCustomCountDownTimer.cancel();
        }
    }
}

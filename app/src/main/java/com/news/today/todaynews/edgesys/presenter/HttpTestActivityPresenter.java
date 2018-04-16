package com.news.today.todaynews.edgesys.presenter;

import com.news.today.http.parser.IResult;
import com.news.today.todaynews.base.DaggerMvpPresenter;
import com.news.today.todaynews.edgesys.lf.IHttpTestContract;
import com.news.today.todaynews.edgesys.lf.ISplashContract;
import com.news.today.todaynews.edgesys.manager.HttpTestManager;
import com.news.today.todaynews.system.JHTask;

import javax.inject.Inject;

/**
 * Created by anson on 2018/4/6.
 */

public class HttpTestActivityPresenter extends DaggerMvpPresenter<ISplashContract.IView> implements IHttpTestContract.IPresenter {

    @Inject
    HttpTestManager manager;

    @Inject
    public HttpTestActivityPresenter(ISplashContract.IView view) {
        super(view);
    }


    @Override
    protected ISplashContract.IView getEmptyView() {
        return ISplashContract.emptyView;
    }


    @Override
    public void getNetData() {
        submitTask(new JHTask<Object>() {

            @Override
            public IResult<Object> onBackground() throws Exception {
                return null;
            }

            @Override
            public void onSuccess(IResult result) {

            }

            @Override
            public boolean onFailure(IResult result) {

                return super.onFailure(result);
            }
        });
    }
}

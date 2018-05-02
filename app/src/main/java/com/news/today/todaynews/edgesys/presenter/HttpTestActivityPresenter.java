package com.news.today.todaynews.edgesys.presenter;

import com.base.rxjava.subscriber.ErrorHandlerSubscriber;
import com.news.today.http.parser.IResult;
import com.news.today.http.util.JsonHelper;
import com.news.today.todaynews.base.DaggerMvpPresenter;
import com.news.today.todaynews.edgesys.entity.XiaoHua;
import com.news.today.todaynews.edgesys.lf.IHttpTestContract;
import com.news.today.todaynews.edgesys.manager.HttpTestManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by anson on 2018/4/6.
 */

public class HttpTestActivityPresenter extends DaggerMvpPresenter<IHttpTestContract.IView> implements IHttpTestContract.IPresenter {

    @Inject
    HttpTestManager manager;


    @Inject
    public HttpTestActivityPresenter(IHttpTestContract.IView view) {
        super(view);
    }


    @Override
    protected IHttpTestContract.IView getEmptyView() {
        return IHttpTestContract.emptyView;
    }


    @Override
    public void getNetData() {
       /* submitTask(new JHTask<XiaoHua>() {
            @Override
            public IResult<XiaoHua> onBackground() throws Exception {
                return manager.getXiaoHuaList(1);
            }

            @Override
            public void onSuccess(IResult<XiaoHua> result) {
                getView().showText(JsonHelper.toJSONString(result.data()));
            }

            @Override
            public boolean onFailure(IResult result) {
                return super.onFailure(result);
            }
        });*/

       manager.getXiaoHuaOb(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
               .subscribe(new ErrorHandlerSubscriber<IResult<XiaoHua>>() {
           @Override
           public void onNext(IResult<XiaoHua> result) {
               getView().showText(JsonHelper.toJSONString(result.data()));
           }
       });
    }
}
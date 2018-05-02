package com.news.today.todaynews.edgesys.presenter;

import com.base.rxjava.RxSchedulers;
import com.base.rxjava.subscriber.ErrorHandlerSubscriber;
import com.news.today.http.parser.IResult;
import com.news.today.http.util.JsonHelper;
import com.news.today.todaynews.base.DaggerMvpPresenter;
import com.news.today.todaynews.edgesys.entity.XiaoHua;
import com.news.today.todaynews.edgesys.lf.IHttpTestContract;
import com.news.today.todaynews.edgesys.manager.HttpTestManager;

import javax.inject.Inject;

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


       manager.getXiaoHuaOb(1).compose(RxSchedulers.<IResult<XiaoHua>>io_main())
               .subscribe(new ErrorHandlerSubscriber<IResult<XiaoHua>>(mContext) {
           @Override
           public void onNext(IResult<XiaoHua> result) {
               getView().showText(JsonHelper.toJSONString(result.data()));
           }
       });
    }
}
package com.news.today.todaynews.edgesys.presenter;

import com.news.today.http.parser.IResult;
import com.news.today.todaynews.base.DaggerMvpPresenter;
import com.news.today.todaynews.edgesys.entity.XiaoHua;
import com.news.today.todaynews.edgesys.lf.IHttpTestContract;
import com.news.today.todaynews.edgesys.manager.HttpTestManager;
import com.news.today.todaynews.system.task.JHTask;

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
        submitTask(new JHTask<XiaoHua>() {
            //子线程
            @Override
            public IResult<XiaoHua> onBackground() throws Exception {
                //网络请求，基本的解析操作
                return manager.getXiaoHuaList(1);
            }
            //主线程
            @Override
            public void onSuccess(IResult<XiaoHua> result) {
                //仅仅是数据回调
                getView().showData(result.data());
            }
            //主线程
            @Override
            public boolean onFailure(IResult result) {
                return super.onFailure(result);
            }
        });
    }
}

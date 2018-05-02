package com.news.today.todaynews.edgesys.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.news.today.todaynews.R;
import com.news.today.todaynews.base.DaggerMvpActivity;
import com.news.today.todaynews.edgesys.lf.IHttpTestContract;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by anson on 2018/4/8.
 */

public class HttpTestActivity extends DaggerMvpActivity implements IHttpTestContract.IView {

    @Inject
    IHttpTestContract.IPresenter mPresenter;

    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_http_test;
    }

    @Override
    public void afterViewBind(View rootView, Bundle savedInstanceState) {
        mPresenter.getNetData();
    }

    @Override
    public void showText(String str) {
        tvContent.setText(str);
    }
}

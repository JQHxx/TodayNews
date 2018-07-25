package com.news.today.todaynews.edgesys.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.news.today.http.util.JsonHelper;
import com.news.today.todaynews.R;
import com.news.today.todaynews.base.DaggerMvpActivity;
import com.news.today.todaynews.edgesys.entity.XiaoHua;
import com.news.today.todaynews.edgesys.lf.IHttpTestContract;
import com.news.today.todaynews.homesys.home.view.MainActivity;

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
    public void showData(XiaoHua str) {
        tvContent.setText(JsonHelper.toJSONString(str));
        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(HttpTestActivity.this, MainActivity.class),200);
            }
        });
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
}

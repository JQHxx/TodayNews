package com.news.today.todaynews.homesys.shanghai.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.news.today.todaynews.R;
import com.news.today.todaynews.base.DaggerMvpFragment;
import com.news.today.todaynews.edgesys.entity.XiaoHua;
import com.news.today.todaynews.edgesys.lf.IHttpTestContract;
import com.news.today.todaynews.homesys.shanghai.MostListAdapter;
import com.news.today.tools.refresh.OnHeaderRefreshListener;
import com.news.today.tools.refresh.ToolsSelfRefreshView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by anson on 2018/4/26.
 */

public class MostListFragment extends DaggerMvpFragment implements IHttpTestContract.IView, OnHeaderRefreshListener {

    @Inject
    IHttpTestContract.IPresenter mPresenter;
    @BindView(R.id.list)
    RecyclerView recyclerView;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_most_list;
    }

    @Override
    public void afterViewBind(View rootView, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPresenter.getNetData();
        mPresenter.getXiaoHuaData().observe(this, new Observer<XiaoHua>() {
            @Override
            public void onChanged(@Nullable XiaoHua xiaoHua) {
                showData(xiaoHua);
            }
        });
    }


    @Override
    public void showData(final XiaoHua str) {
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(new MostListAdapter(R.layout.layout_animation,str.getResult().getData()));
        }
    }

    @Override
    public void onHeaderRefresh(ToolsSelfRefreshView view) {
        mPresenter.getNetData();
    }
}

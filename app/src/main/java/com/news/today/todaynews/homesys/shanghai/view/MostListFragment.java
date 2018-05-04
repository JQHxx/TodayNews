package com.news.today.todaynews.homesys.shanghai.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.news.today.todaynews.R;
import com.news.today.todaynews.base.DaggerMvpFragment;
import com.news.today.todaynews.edgesys.entity.XiaoHua;
import com.news.today.todaynews.edgesys.lf.IHttpTestContract;
import com.news.today.todaynews.homesys.shanghai.MostListAdapter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by anson on 2018/4/26.
 */

public class MostListFragment extends DaggerMvpFragment implements IHttpTestContract.IView {

    @Inject
    IHttpTestContract.IPresenter mPresenter;
    @BindView(R.id.rcv_most_list)
    RecyclerView rcvMostList;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_most_list;
    }

    @Override
    public void afterViewBind(View rootView, Bundle savedInstanceState) {
        mPresenter.getNetData();
    }


    @Override
    public void showData(final XiaoHua str) {
        if (rcvMostList.getAdapter() == null) {
            rcvMostList.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvMostList.setAdapter(new MostListAdapter(R.layout.layout_animation,str.getResult().getData()));
        }
    }

}

package com.news.today.todaynews.homesys.shanghai;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;


import com.news.today.todaynews.R;
import com.news.today.todaynews.edgesys.entity.XiaoHua;
import com.news.today.tools.adapter.BaseToolsAdapter;
import com.news.today.tools.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by anson on 2018/5/3.
 */

public class MostListAdapter extends BaseToolsAdapter<XiaoHua.ResultBean.DataBean> {

    public MostListAdapter(@LayoutRes int layoutResId, @Nullable List<XiaoHua.ResultBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, XiaoHua.ResultBean.DataBean item) {
        helper.setText(R.id.tweetName, item.getUnixtime() + "");
        helper.setText(R.id.tweetText, item.getContent());
        helper.setText(R.id.tweetDate, item.getUpdatetime());
    }
}

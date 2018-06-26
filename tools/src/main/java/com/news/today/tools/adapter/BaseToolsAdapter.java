package com.news.today.tools.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anson on 2018/5/7.
 */

public abstract class BaseToolsAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mData;
    protected int mLayoutResId;

    /**
     * 唯一的构造函数
     * @param layoutResId  布局id
     * @param data  数据
     */
    public BaseToolsAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder,mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected abstract void convert(BaseViewHolder helper, T item);
}

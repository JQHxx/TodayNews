package com.news.today.todaynews.homesys.shanghai.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.IRefreshHeader;
import com.news.today.todaynews.R;

/**
 * Created by anson on 2018/5/17.
 */

public class LofterRefreshView extends LinearLayout implements IRefreshHeader {

    private LinearLayout mContainer;

    public LofterRefreshView(@NonNull Context context) {
        super(context);
        initView();
    }


    public LofterRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LofterRefreshView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_lofter_loading, null);
        addView(mContainer, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onReset() {

    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onRefreshing() {

    }

    @Override
    public void onMove(float offSet, float sumOffSet) {

    }

    @Override
    public boolean onRelease() {
        return false;
    }

    @Override
    public void refreshComplete() {

    }

    @Override
    public View getHeaderView() {
        return null;
    }

    @Override
    public int getVisibleHeight() {
        return 0;
    }

    @Override
    public int getVisibleWidth() {
        return 0;
    }
}

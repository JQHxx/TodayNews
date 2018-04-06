package com.news.today.mvp.lf.view;

import android.os.Bundle;
import android.view.View;

/**
 * Created by anson on 2018/4/6.
 */

public interface IBindView {
    /**
     * 取得布局
     *
     * @return
     */
    int getRootLayoutId();

    /**
     * 视图绑定之后触发
     *
     * @param savedInstanceState
     */
    void afterViewBind(View rootView, Bundle savedInstanceState);

    /**
     * 绑定view
     *
     * @param rootView
     */
    void bindView(View rootView);
}

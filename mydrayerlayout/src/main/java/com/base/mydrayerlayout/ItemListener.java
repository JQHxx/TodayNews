package com.base.mydrayerlayout;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WangHao
 *
 * @ 创建时间 2018/4/9  16:43
 */

public interface ItemListener {
    /**
     * 对每个 MyDrawSideView 的 child view 进行样式变换进行监听
     *
     * @param sideBar     SideBar
     * @param itemView    child view
     * @param touchY      当前手指按下的 y 位置
     * @param slideOffset 当前 drawerLayout 的 slideOffset
     */
    void apply(ViewGroup sideBar, View itemView, float touchY, float slideOffset);
}

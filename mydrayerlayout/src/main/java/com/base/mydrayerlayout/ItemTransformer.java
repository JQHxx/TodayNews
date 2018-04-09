package com.base.mydrayerlayout;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WangHao
 *
 * @ 创建时间 2018/4/9  16:55
 */

public class ItemTransformer implements ItemListener{
    private float maxTranslationX;

    public ItemTransformer(float maxTranslationX) {
        this.maxTranslationX = maxTranslationX;
    }
    /**
     * 对子控件进行偏移
     * @param sideBar 父控件
     * @param itemView 要偏移的子控件
     * @param touchY 偏移最大的 Y 坐标
     * @param slideOffset 偏移比例
     */
    @Override
    public void apply(ViewGroup sideBar, View itemView, float touchY, float slideOffset) {
        //偏移距离
        float translationX=0;
        //计算子控件的中点 Y 坐标
        int centerY=itemView.getTop()+itemView.getHeight()/2;
        //计算子控件中点与手指触摸的 Y 方向距离
        float distance=Math.abs(touchY-centerY);

        //计算子控件偏移距离
        float scale=distance/sideBar.getHeight()*3;//3   放大系数  距离中心点距离与 sideBar 的 1/3 对比
        translationX=maxTranslationX * (1f - scale);
        itemView.setTranslationX(translationX);
    }
}

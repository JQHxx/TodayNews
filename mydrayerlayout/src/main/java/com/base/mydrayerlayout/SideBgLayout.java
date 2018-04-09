package com.base.mydrayerlayout;

import android.graphics.Color;
import android.widget.RelativeLayout;

/**
 * Created by WangHao
 *
 * @ 创建时间 2018/4/9  12:53
 */

public class SideBgLayout extends RelativeLayout{
    //侧滑控件
    private  MyDrawSideView mDrawSideView;
    //背景控件
    private SideBgView mBgView;
    public SideBgLayout(MyDrawSideView myDrawSideView) {
        super(myDrawSideView.getContext());
        init(myDrawSideView);
    }

    private void init(MyDrawSideView myDrawSideView) {
        this.mDrawSideView=myDrawSideView;
        //把SideBgView的 宽高移交给外部的SideBgLayout
        setLayoutParams(myDrawSideView.getLayoutParams());
        //背景添加进去
        mBgView = new SideBgView(getContext());
        addView(mBgView,0,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //把把SideBgView  的背景颜色取出来    设置给 SideBgLayout   SideBgView弄成透明
        mBgView.setDrawable(mDrawSideView.getBackground());
        mDrawSideView.setBackgroundColor(Color.TRANSPARENT);
        addView(myDrawSideView,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 传递偏移Y
     * @param y
     * @param slideOffset
     */
    public void setTouchY(float y, float slideOffset) {
        mDrawSideView.setTouchY(y,slideOffset);
        mBgView.setTouchY(y, slideOffset);

    }
}

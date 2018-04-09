package com.base.mydrayerlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by WangHao
 *
 * @ 创建时间 2018/4/9  12:44
 * 管理侧滑菜单的每一个子控件。当手指触摸屏幕进行滑动时候，背景变化，每个子控件做出相应的位移（位移大小没有具体参考值，需要对实际情况进行调整）。
 * 另外是在手指松开的时候，会去判断调用哪一个子控件的触摸方法（只根据 Y 进行判断，没有进行 X 的处理）。
 */

public class MyDrawSideView extends LinearLayout {
    private boolean opened = false;
    //子控件的最大偏移量
    private float maxTranslationX;

    private ItemListener mListener;
    private onMotionListener motionListener;
    public MyDrawSideView(Context context) {
        this(context, null);
    }

    public MyDrawSideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        //设置摆放方向为竖直方向
        setOrientation(VERTICAL);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SideBar);
            maxTranslationX = typedArray.getDimension(R.styleable.SideBar_maxTranslationX, 0);
            mListener = new ItemTransformer(maxTranslationX);
            typedArray.recycle();
        }
    }

    /**
     * 对子控件进行相应的偏移
     *
     * @param y       当前触摸点 Y 坐标
     * @param percent 侧滑栏菜单滑出的百分比
     */
    public void setTouchY(float y, float percent) {
        //遍历全部子控件  给每一个子控件进行偏移
        //如果slideOffset =1   侧滑菜单全部出来了
        opened = percent == 1;
        boolean found = false;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setPressed(false);

            boolean isHover = opened && y > child.getTop() && y < child.getBottom();
            if (isHover) {
                found = true;
                if (motionListener == null || !motionListener.onHover(child, i)) {
                    child.setPressed(true);
                }
                //回调调用层
                mListener.apply((ViewGroup) getParent(), child, y, percent);
            }

        }

        if (opened && !found && motionListener != null) {
            motionListener.onHover(null, -1);
        }
    }

    /**
     * 手指松开的时候处理
     */
    public void onMotionUp() {
        for (int i = 0; opened && i < getChildCount(); i++) {
            View child = getChildAt(i);
            //要判断  y坐落在哪一个子控件    松手的那一刻  进行回调  跳转其他页面
            if (child.isPressed()) {
                if (motionListener == null || !motionListener.onSelect(child, i)) {
                    child.performClick();
                }
            }
        }
        if (motionListener != null) {
            motionListener.onCancel();
        }
    }
    /**
     * 子view偏移监听设置
     */
    public void setItemListener(ItemListener listener) {
        this.mListener = listener;
    }
    /**
     * 子view选中状态监听设置
     */
    public void setOnMotionListener(onMotionListener motionListener) {
        this.motionListener = motionListener;
    }
}

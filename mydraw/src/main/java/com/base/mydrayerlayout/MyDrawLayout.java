package com.base.mydrayerlayout;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by WangHao
 *
 * @ 创建时间 2018/4/9  12:55
 *
 * 摆放
 *
 * 绘制
 *
 * 事件
 */

public class MyDrawLayout extends DrawerLayout implements DrawerLayout.DrawerListener{
    /**
     * 侧滑控件
     */
    private MyDrawSideBar mSideView;
    private View          mView;
    private SideBgLayout  mBgLayout;
    private float         slideOffset;
    private float         y;
    public MyDrawLayout(Context context) {
        super(context);
    }

    public MyDrawLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        //对 child view 进行分裂
        classifyChildren();

        //先移除 myDrawSlideBar      再来添加  RelativeLayout
        removeView(mSideView);

        //实例化  背景RealativeLayout
        mBgLayout=new SideBgLayout(mSideView);
        //偷梁换柱
        addView(mBgLayout);
        addDrawerListener(this);
    }
    /**
     * 对 child view 进行分裂,得到 View 和 MyDrawSideView
     */
    private void classifyChildren() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof MyDrawSideBar) {
                mSideView = (MyDrawSideBar) child;
            } else {
                mView = child;
            }
        }
        if (mView == null) {
            throw new IllegalStateException("Content layout not found.");
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //不能在 OnTouch 中获取 Y 的坐标，避免事件被子控件消费的时候，获取不到
        y=ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_UP){
            mSideView.onMotionUp();
            return super.dispatchTouchEvent(ev);
        }
        //没有打开之前 不拦截     打开之后拦不拦截  大于1  后  内容区域不再进行偏移
        if (slideOffset < 1) {
            return super.dispatchTouchEvent(ev);
        }else {
            //等于  1
            mBgLayout.setTouchY(y,slideOffset);
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     *
     * @param drawerView
     * @param slideOffset  滑动的百分比   Move
     */
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        this.slideOffset=slideOffset;
        mBgLayout.setTouchY(y,slideOffset);
        //针对内容区域进行破偏移
        float contentViewoffset=drawerView.getWidth()*slideOffset/2;
        mView.setTranslationX(contentViewoffset);
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}

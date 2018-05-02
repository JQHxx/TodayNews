package com.base.mydrayerlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Path;

/**
 * Created by WangHao
 *
 * @ 创建时间 2018/4/9  12:38
 * <p>
 * 做背景的动态效果
 */

public class SideBgView extends View {

    private Paint mPaint;
    private Path  mPath;

    private Drawable mDrawable;

    public SideBgView(Context context) {
        this(context, null);
    }

    public SideBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化画笔和Path
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 构建Paint时直接加上去锯齿属性
        mPath = new Path();
    }

    /**
     * 设置当前触摸点的 Y 坐标，从而改变背景波浪
     *
     * @param y       当前触摸点 Y 坐标
     * @param percent 侧滑栏菜单滑出的百分比
     */
    public void setTouchY(float y, float percent) {
        //重置路径
        mPath.reset();

        //获取侧滑菜单滑出来的宽度
        float width = getWidth() * percent;
        float height = getHeight();

        //计算贝塞尔曲线 Y 方向超出去的距离（8 效果可能会好一些）
        float offsetY = height / 8;
        //计算被赛尔曲线 X 方向偏移的距离（也是为了效果好一些）
        float offsetX = width / 2;

        mPath.lineTo(offsetX, -offsetY);
        mPath.quadTo(width * 3 / 2, y, offsetX, height + offsetY);
        mPath.lineTo(0, height);
        mPath.close();

        invalidate();

    }

    /**
     * 设置背景
     * （由 MyDrawSideView 传递给 SideBgLayout 的 background ）
     *
     * @param drawable
     */
    public void setDrawable(Drawable drawable) {
        if (drawable instanceof ColorDrawable) {
            //支持背景为颜色
            mPaint.setColor(((ColorDrawable) drawable).getColor());
        } else {
            //支持背景为图片
            mDrawable = drawable;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawable == null) {
            //绘制背景为颜色的
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(mPath,mPaint);
        }else {
            //绘制背景为图片的，根据 Path 对 图片进行截取
            mDrawable.setBounds(0,0,getWidth(),getHeight());
            canvas.clipPath(mPath);
            mDrawable.draw(canvas);
        }
    }
}

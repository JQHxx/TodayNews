package com.news.today.todaynews.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by anson on 2018/4/3.
 */
public class CustomVideoView extends VideoView {

    //主要是用于直接new出来对象
    public CustomVideoView(Context context) {
        super(context);
    }
    //主要用于在xml文件中定义，支持自定义属性
    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //也是用于在xml文件中定义，支持style样式
    public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //getSuggestedMinimumWidth是根据设置的背景跟最小尺寸得到一个备用的参考尺寸
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        //复写onMeasure 最重要的就是这个方法，设置测量的宽和高值，不随视频大小而变化
        setMeasuredDimension(width, height);
    }
}

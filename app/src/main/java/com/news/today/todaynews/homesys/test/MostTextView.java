package com.news.today.todaynews.homesys.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.news.today.todaynews.R;

/**
 * Created by anson on 2018/4/27.
 */

public class MostTextView  extends LinearLayout implements View.OnClickListener {
    /** default text show max lines */
    private static final int DEFAULT_MAX_LINE_COUNT = 2;

    private static final int COLLAPSIBLE_STATE_NONE = 0;
    private static final int COLLAPSIBLE_STATE_SHRINKUP = 1;
    private static final int COLLAPSIBLE_STATE_SPREAD = 2;

    private TextView desc;
    private TextView descOp;

    private String shrinkup;
    private String spread;
    private int mState;
    private boolean flag;
    private OnTextChangeListener onTextChangeListener;

    public MostTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        shrinkup="收起";
        spread="查看更多";
        View view = inflate(context, R.layout.collapsible_textview, this);
        view.setPadding(0, -1, 0, 0);
        desc = (TextView) view.findViewById(R.id.desc_tv);
        descOp = (TextView) view.findViewById(R.id.desc_op_tv);
        descOp.setOnClickListener(this);
    }

    public MostTextView(Context context) {
        this(context, null);
    }

    public void setTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }

    public interface OnTextChangeListener {
        void shouqi();

        void zhankai();
    }
    /**
     1.
     2. 对外提供暴漏的方法，为文本提供数据
     3. @param charSequence  文本内容是什么
     4. @param bufferType
     */
    public final void setDesc(CharSequence charSequence, TextView.BufferType bufferType) {
        desc.setText(charSequence, bufferType);
        mState = COLLAPSIBLE_STATE_SPREAD;
        requestLayout();
    }

    @Override
    public void onClick(View v) {
        flag = false;
        requestLayout();
    }

    public void close() {
        flag = false;
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!flag) {
            flag = true;
            if (desc.getLineCount() <= DEFAULT_MAX_LINE_COUNT) {
                mState = COLLAPSIBLE_STATE_NONE;
                descOp.setVisibility(View.GONE);
                desc.setMaxLines(DEFAULT_MAX_LINE_COUNT + 1);
            } else {
                post(new InnerRunnable());
            }
        }
    }

    class InnerRunnable implements Runnable {
        @Override
        public void run() {
            if (mState == COLLAPSIBLE_STATE_SPREAD) {
                desc.setMaxLines(DEFAULT_MAX_LINE_COUNT);
                descOp.setVisibility(View.VISIBLE);
                descOp.setText(spread);
                if (onTextChangeListener != null) {
                    onTextChangeListener.shouqi();
                }
                mState = COLLAPSIBLE_STATE_SHRINKUP;
            } else if (mState == COLLAPSIBLE_STATE_SHRINKUP) {
                desc.setMaxLines(Integer.MAX_VALUE);
                descOp.setVisibility(View.VISIBLE);
                descOp.setText(shrinkup);
                if (onTextChangeListener != null) {
                    onTextChangeListener.zhankai();
                }
                mState = COLLAPSIBLE_STATE_SPREAD;
            }
        }
    }
}


package com.news.today.todaynews.homesys.video.sdk.mediaplayer;

import com.news.today.todaynews.homesys.video.sdk.lf.IPlayerError;

/**
 * Created by Ryan Hu on 2017/11/27.
 */

public class GoogleMediaPlayerError implements IPlayerError {

    private int mArcCode;
    private int mArcExtra;
    private Throwable mCause;

    /* package */ GoogleMediaPlayerError(int code, int extra) {
        mArcCode = code;
        mArcExtra = extra;
    }

    /* package */ GoogleMediaPlayerError(Throwable cause) {
        mCause = cause;
    }

    @Override
    public int getCode() {
        return mArcCode;
    }

    @Override
    public String getMessage() {
        return String.format("Arc error (%d, %d), cause: $s", mArcCode, mArcExtra, mCause.getMessage());
    }

    @Override
    public Throwable getCause() {
        return null;
    }
}

package com.news.today.todaynews.homesys.video.sdk.lf;

import android.os.Parcelable;

/**
 * 播放数据源
 *
 * Created by anson on 2017/11/24.
 */

public interface IMediaSource {

    void setRemoteUrl(String url);

    String getRemoteUrl();
}

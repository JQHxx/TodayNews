package com.news.today.todaynews.homesys.video.sdk.mediaplayer;

import com.news.today.todaynews.homesys.video.sdk.lf.IMediaSource;

/**
 * Created by anson on 2018/5/9.
 */

public class GoogleMediaPlayerSource implements IMediaSource {

    String url;

    @Override
    public void setRemoteUrl(String url) {
        this.url = url;
    }

    @Override
    public String getRemoteUrl() {
        return url;
    }
}

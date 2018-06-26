package com.news.today.todaynews.homesys.video.sdk.mediaplayer;

import com.news.today.todaynews.homesys.video.sdk.lf.IMediaSource;
import com.news.today.todaynews.homesys.video.sdk.lf.IPlayer;
import com.news.today.todaynews.homesys.video.sdk.lf.IPlayerFactory;

/**
 * Created by anson on 2018/5/9.
 */

public class GoogleMediaPlayerFactory implements IPlayerFactory {

    @Override
    public IPlayer createPlayer(IMediaSource source) {
        if (source instanceof GoogleMediaPlayerSource) {
            return new GoogleMediaPlayer();
        }
        return null;
    }
}


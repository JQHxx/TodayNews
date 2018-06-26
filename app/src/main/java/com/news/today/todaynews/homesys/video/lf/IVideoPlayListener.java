package com.news.today.todaynews.homesys.video.lf;

import com.news.today.todaynews.homesys.video.sdk.PlayerState;
import com.news.today.todaynews.homesys.video.sdk.lf.IPlayerError;

/**
 * Created by anson on 2018/5/9.
 */

public interface IVideoPlayListener {
    void onStateChanged (PlayerState state);

    void onDurationChanged (int msec);

    void onSeekComplete ();

    void onError (IPlayerError error);
}

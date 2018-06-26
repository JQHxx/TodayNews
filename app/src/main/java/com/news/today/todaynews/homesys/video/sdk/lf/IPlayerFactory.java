package com.news.today.todaynews.homesys.video.sdk.lf;

/**
 * Created by anson on 2017/11/27.
 */

public interface IPlayerFactory {

    /**
     * 为指定的数据源创建播放器，如果不支持当前指定的源，则返回null
     *
     * @param source
     * @return
     */
    IPlayer createPlayer(IMediaSource source);
}

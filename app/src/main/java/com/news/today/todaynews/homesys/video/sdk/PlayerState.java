package com.news.today.todaynews.homesys.video.sdk;

import com.news.today.todaynews.homesys.video.sdk.lf.IMediaSource;
import com.news.today.todaynews.homesys.video.sdk.lf.IPlayer;

/**
 * 播放器状态枚举值
 *
 * Created by anson on 2017/11/24.
 */

public enum PlayerState {

    /**
     * 初始状态，新建播放器或调用{@link IPlayer#reset()}方法到达此状态
     */
    IDLE,

    /**
     * 准备中状态，调用{@link IPlayer#prepare(IMediaSource)}到达此状态
     */
    PREPARING,

    /**
     * 准备完成，可以开始播放了
     */
    PREPARED,

    /**
     * 播放中
     */
    STARTED,

    /** 暂停状态 **/
    PAUSED,

    /** 停止状态 **/
    STOPPED,

    /** 完成状态 **/
    COMPLETED,

    /**
     * 停止状态
     *
     * @see IPlayer#release()
     **/
    END,

    /** 错误状态 **/
    ERROR
}

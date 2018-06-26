package com.news.today.todaynews.homesys.video.sdk.mediaplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.SurfaceView;

import com.news.today.todaynews.helper.ContextHelper;
import com.news.today.todaynews.homesys.video.lf.IVideoPlayListener;
import com.news.today.todaynews.homesys.video.sdk.PlayerState;
import com.news.today.todaynews.homesys.video.sdk.lf.IMediaSource;
import com.news.today.todaynews.homesys.video.sdk.lf.IPlayer;

import java.io.IOException;

/**
 * Created by anson on 2018/5/9.
 */

public class GoogleMediaPlayer implements IPlayer, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {

    private final MediaPlayer mediaPlayer;
    private IVideoPlayListener callback;

    public GoogleMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
    }


    @Override
    public void setDisplay(SurfaceView surfaceView) {

    }

    @Override
    public void setCallback(IVideoPlayListener callback) {
        this.callback = callback;
    }

    @Override
    public void reset() {

    }

    @Override
    public void prepare(IMediaSource source) {
        try {
            mediaPlayer.setDataSource(ContextHelper.getAppContext(), Uri.parse(source.getRemoteUrl()));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void stop() {

    }

    @Override
    public void seek(int msec) {

    }

    @Override
    public void release() {

    }

    @Override
    public PlayerState getState() {
        return null;
    }

    @Override
    public void setVolume(int volume) {

    }

    @Override
    public int getVolume() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void enablePositionReport(boolean enabled) {

    }

    @Override
    public boolean supportBufferProgress() {
        return false;
    }

    @Override
    public int getVideoWidth() {
        return 0;
    }

    @Override
    public int getVideoHeight() {
        return 0;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        if (callback != null) {
            callback.onStateChanged(PlayerState.PREPARED);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (callback != null) {
            callback.onSeekComplete();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (callback != null) {
            callback.onError(new GoogleMediaPlayerError(what,extra));
        }
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (callback != null) {
            callback.onDurationChanged(mp.getDuration() * percent / 100);
        }
    }
}

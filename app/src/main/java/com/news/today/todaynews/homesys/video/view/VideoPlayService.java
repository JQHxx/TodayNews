package com.news.today.todaynews.homesys.video.view;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.news.today.todaynews.R;
import com.news.today.todaynews.homesys.video.lf.IVideoPlayListener;
import com.news.today.todaynews.homesys.video.sdk.PlayerState;
import com.news.today.todaynews.homesys.video.sdk.lf.IMediaSource;
import com.news.today.todaynews.homesys.video.sdk.lf.IPlayer;
import com.news.today.todaynews.homesys.video.sdk.lf.IPlayerFactory;
import com.news.today.todaynews.homesys.video.sdk.mediaplayer.GoogleMediaPlayerFactory;
import com.news.today.todaynews.homesys.video.sdk.mediaplayer.GoogleMediaPlayerSource;
import com.news.today.todaynews.utils.Strings;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anson on 2018/5/9.
 */

public class VideoPlayService extends Service{

    private IBinder mBinder = new LocalBinder();
    private HashMap<String, IVideoPlayListener> mListeners;
    private SoftReference<HashMap<String, IVideoPlayListener>> weakReference;
    private PlayerState mState = PlayerState.IDLE;
    private IMediaSource mediaSource;

    public class LocalBinder extends Binder {

        public VideoPlayService getService () {
            return VideoPlayService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //这里写死一个播放源
        mediaSource = new GoogleMediaPlayerSource();
        mediaSource.setRemoteUrl(Strings.androidResouces + this.getPackageName() + File.separator + R.raw.haha);
    }

    //添加监听
    public void setVideoPlayCallBack(String key,IVideoPlayListener loadInfoListener) {
        if (mListeners == null) {
            mListeners = new HashMap<>();
        }
        if (weakReference == null) {
            weakReference = new SoftReference<>(mListeners);
        }
        weakReference.get().put(key, loadInfoListener);
    }

    //移除监听
    public void removeVideoPlayCallback(String key) {
        if (weakReference != null && weakReference.get() != null) {
            for (Map.Entry<String, IVideoPlayListener> entry : weakReference.get().entrySet()) {
                if (TextUtils.equals(entry.getKey(), key)) {
                    weakReference.get().remove(key);
                    break;
                }
            }
        }
    }
    //处理播放/暂停逻辑
    public void handlePlayOrStop() {
        switch (mState) {
            case IDLE:
                //创建播放器然后播放
                IPlayerFactory playerFactory = new GoogleMediaPlayerFactory();
                IPlayer player = playerFactory.createPlayer(mediaSource);
                player.prepare(mediaSource);
                break;
            case STARTED:
                //暂停播放
                break;
            case PAUSED:
                //开始播放
                break;
            case COMPLETED:
            case ERROR:
                //重新播放
                break;
        }
    }
}

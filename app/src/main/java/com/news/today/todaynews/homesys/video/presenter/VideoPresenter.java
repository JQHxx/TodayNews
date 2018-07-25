package com.news.today.todaynews.homesys.video.presenter;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.news.today.todaynews.base.DaggerMvpPresenter;
import com.news.today.todaynews.helper.ContextHelper;
import com.news.today.todaynews.homesys.video.IVideoContract;
import com.news.today.todaynews.homesys.video.lf.IVideoPlayListener;
import com.news.today.todaynews.homesys.video.sdk.PlayerState;
import com.news.today.todaynews.homesys.video.sdk.lf.IPlayerError;
import com.news.today.todaynews.homesys.video.view.VideoPlayService;

import javax.inject.Inject;

/**
 * Created by anson on 2018/5/9.
 */

public class VideoPresenter extends DaggerMvpPresenter<IVideoContract.IView> implements IVideoContract.IPresenter, IVideoPlayListener {

    private VideoPlayService videoPlayerService;

    @Inject
    public VideoPresenter(IVideoContract.IView view) {
        super(view);
    }

    @Override
    protected IVideoContract.IView getEmptyView() {
        return IVideoContract.emptyView;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            onServiceAttached((VideoPlayService.LocalBinder)service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            onServiceDetached();
        }
    };

    @Override
    public void onStart() {
        Intent serviceIntent = new Intent(ContextHelper.getAppContext(), VideoPlayService.class);
        ContextHelper.getAppContext().bindService(serviceIntent, connection, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void handlePlayClick() {
        if (videoPlayerService != null) {
            videoPlayerService.handlePlayOrStop();
        }
    }

    private void onServiceAttached (VideoPlayService.LocalBinder service) {
        videoPlayerService = service.getService();
        videoPlayerService.setVideoPlayCallBack(getView().toString(),this);
    }

    private void onServiceDetached () {
        if (videoPlayerService != null) {
            videoPlayerService.removeVideoPlayCallback(getView().toString());
            videoPlayerService = null;
        }
    }

    @Override
    public void onStateChanged(PlayerState state) {
        switch (state) {
            case STARTED:
//                getView().showStarted();
                break;
//            case ERROR:
//            case END:
//            case COMPLETED:
//            case IDLE:
//            case PREPARED:
//            case PREPARING:

        }
    }

    @Override
    public void onDurationChanged(int msec) {

    }

    @Override
    public void onSeekComplete() {

    }

    @Override
    public void onError(IPlayerError error) {

    }
}

package com.news.today.todaynews.edgesys.view;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.news.today.todaynews.R;
import com.news.today.todaynews.base.DaggerMvpActivity;
import com.news.today.todaynews.edgesys.lf.ISplashContract;
import com.news.today.todaynews.homesys.home.view.MainActivity;
import com.news.today.todaynews.utils.Integers;
import com.news.today.todaynews.utils.Strings;
import com.news.today.todaynews.widget.CustomVideoView;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by anson on 2018/4/3.
 */

public class SplashActivity extends DaggerMvpActivity implements ISplashContract.IView {

    @Inject
    ISplashContract.IPresenter mPresenter;
    @BindView(R.id.vv_splash)
    CustomVideoView mVideoView;
    @BindView(R.id.tv_over)
    TextView mTextView;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void afterViewBind(View rootView, Bundle savedInstanceState) {
        initVideo();
        mPresenter.registerNormalCountDown(Integers.splashNums);
    }

    private void initVideo() {
        mVideoView.setVideoURI(Uri.parse(Strings.androidResouces + this.getPackageName() + File.separator + R.raw.splash));
        mVideoView.start();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showCountDownTime(String countDownTime) {
        mTextView.setText(countDownTime);
    }

    @Override
    public void showTimeOver() {
        mTextView.setText(R.string.str_splash_finish);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoView.isPlaying()) {
                    mVideoView.stopPlayback();
                }
                MainActivity.start(SplashActivity.this);
//                startActivity(new Intent(SplashActivity.this,HttpTestActivity.class));
                finish();
            }
        });
    }
}

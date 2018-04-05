package com.news.today.todaynews.edgesys;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.news.today.todaynews.homesys.MainActivity;
import com.news.today.todaynews.utils.CustomCountDownTimer;
import com.news.today.todaynews.widget.CustomVideoView;
import com.news.today.todaynews.R;
import com.news.today.todaynews.utils.Integers;
import com.news.today.todaynews.utils.Strings;

import java.io.File;

/**
 * Created by anson on 2018/4/3.
 */

public class SplashActivity extends AppCompatActivity {

    private CustomVideoView mVideoView;
    private TextView mTextView;
    private CustomCountDownTimer mCustomCountDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initVideo();
        registerNormalCountDown(Integers.splashNums);
    }


    private void initView() {
        mVideoView = (CustomVideoView) findViewById(R.id.vv_splash);
        mTextView = (TextView) findViewById(R.id.tv_over);
    }

    private void initVideo() {
        mVideoView.setVideoURI(Uri.parse(Strings.androidResouces + this.getPackageName() + File.separator+ R.raw.splash));
        mVideoView.start();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.start();
            }
        });
    }

    private void registerNormalCountDown(int time) {
        mCustomCountDownTimer = new CustomCountDownTimer(time,
                new CustomCountDownTimer.ICountDownHandler() {
                    @Override
                    public void onTicker(int time) {
                        mTextView.setText(getString(R.string.str_splash_count_down,time));
                    }

                    @Override
                    public void onFinish() {
                        mTextView.setText(R.string.str_splash_finish);
                        mTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mVideoView.isPlaying()) {
                                    mVideoView.stopPlayback();
                                }
                                MainActivity.start(SplashActivity.this);
                                finish();
                            }
                        });
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCustomCountDownTimer) {
            mCustomCountDownTimer.cancel();
        }
    }
}

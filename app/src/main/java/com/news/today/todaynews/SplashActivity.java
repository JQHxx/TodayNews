package com.news.today.todaynews;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.VideoView;

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
    }

    private void initView() {
        mVideoView = (CustomVideoView) findViewById(R.id.vv_splash);
        mTextView = (TextView) findViewById(R.id.tv_over);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.splash));
        mVideoView.start();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.start();
            }
        });
        registerNormalCountDown(3);

//        mVideoView.setOnCompletionListener { welcome_videoview.start() }
//        welcome_button.setOnClickListener {
//            if (welcome_videoview.isPlaying) {
//                welcome_videoview.stopPlayback()
//            }
//            finish()
//        }
    }

    private void registerNormalCountDown(int time) {
        mCustomCountDownTimer = new CustomCountDownTimer(time,
                new CustomCountDownTimer.ICountDownHandler() {
                    @Override
                    public void onTicker(int time) {
                        mTextView.setText(String.format("%d秒",time));
                    }

                    @Override
                    public void onFinish() {
                        mTextView.setText("跳过");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCustomCountDownTimer){
            mCustomCountDownTimer.cancel();
        }
    }
}

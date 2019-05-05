package com.news.today.todaynews.homesys.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.news.today.todaynews.R;
import com.news.today.todaynews.base.DaggerMvpFragment;

import butterknife.BindView;

/**
 * Created by anson on 2018/4/25.
 */

public class HangZhouFragment extends DaggerMvpFragment {

    @BindView(R.id.tv_home_test)
    ImageView tvHomeTest;


    public static HangZhouFragment newInstance() {
        HangZhouFragment homeFragment = new HangZhouFragment();
        return homeFragment;
    }


    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_hangzhou;
    }

    @Override
    public void afterViewBind(View rootView, Bundle savedInstanceState) {
        tvHomeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animationSet = new AnimationSet(false);
                animationSet.setFillAfter(false);

                //旋转动画
                RotateAnimation rotateAnimation = new RotateAnimation(0.0F, -30F,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                animationSet.addAnimation(rotateAnimation);

                Animation scaleAnimation = new ScaleAnimation(1, 1.2F, 1, 1.2F,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                animationSet.addAnimation(scaleAnimation);

                for(Animation animation : animationSet.getAnimations()) {
                    animation.setDuration(1000);
                }
                tvHomeTest.startAnimation(animationSet);
            }
        });
    }
}

package com.news.today.todaynews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.base.mydrayerlayout.ITouchUpListener;
import com.base.mydrayerlayout.MyDrawLayout;
import com.base.mydrayerlayout.MyDrawSideBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.iv_background)
    ImageView       mIvBackground;
    @BindView(R.id.cl_picture)
    CircleImageView mClPicture;
    @BindView(R.id.tv_name)
    TextView        mTvName;
    @BindView(R.id.tv_friend_circle)
    TextView        mTvFriendCircle;
    @BindView(R.id.tv_wallet)
    TextView        mTvWallet;
    @BindView(R.id.tv_coupon)
    TextView        mTvCoupon;
    @BindView(R.id.tv_friends)
    TextView        mTvFriends;
    @BindView(R.id.tv_sets)
    TextView        mTvSets;
    @BindView(R.id.ms_bar)
    MyDrawSideBar   mMsBar;
    @BindView(R.id.ml_layout)
    MyDrawLayout    mMlLayout;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        setListener();
    }

    private void setListener() {
        mMsBar.setITouchUpListener(new ITouchUpListener() {
            @Override
            public boolean onHover(@Nullable View view, int index) {
                //Toast.makeText(MainActivity.this, "弹出", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onSelect(View view, int index) {
                Toast.makeText(MainActivity.this, String.format("%d selected", index), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

/*
    @OnClick({R.id.iv_background, R.id.tv_friend_circle, R.id.tv_wallet, R.id.tv_coupon, R.id.tv_friends, R.id.tv_sets})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_background:
                break;
            case R.id.tv_friend_circle:
                Toast.makeText(MainActivity.this, "朋友圈", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_wallet:
                Toast.makeText(MainActivity.this, "钱包", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_coupon:
                Toast.makeText(MainActivity.this, "优惠券", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_friends:
                Toast.makeText(MainActivity.this, "好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_sets:
                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }
}

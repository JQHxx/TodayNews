package com.news.today.todaynews.homesys.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.news.today.todaynews.R;
import com.news.today.todaynews.base.DaggerMvpActivity;
import com.news.today.todaynews.base.DaggerMvpFragment;
import com.news.today.todaynews.homesys.home.lf.IMainActivityContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends DaggerMvpActivity implements IMainActivityContract.IView {

    @Inject
    IMainActivityContract.IPresenter mPresenter;

    @BindView(R.id.fl_main_content)
    FrameLayout flMainContent;
    @BindView(R.id.fl_main_bottom)
    FrameLayout flMainBottom;
    @BindView(R.id.fab_main_but)
    FloatingActionButton fabMainBut;
    @BindView(R.id.rb_main_nav_home)
    RadioButton rbMainNavHome;
    @BindView(R.id.rb_main_nav_car_source)
    RadioButton rbMainNavCarSource;
    @BindView(R.id.rg_main_nav_group)
    RadioGroup rgMainNavGroup;
    @BindView(R.id.rb_main_nav_home_beijing)
    RadioButton rbMainNavHomeBeijing;
    @BindView(R.id.rb_main_nav_car_source_shenzhen)
    RadioButton rbMainNavCarSourceShenzhen;
    @BindView(R.id.rg_main_nav_group_change)
    RadioGroup rgMainNavGroupChange;
    @BindView(R.id.fab_main_but_)
    FloatingActionButton actionButton;
    private boolean isChangeFragment = false;


    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    public void show() {
        actionButton.setVisibility(View.VISIBLE);
    }

    public void hide() {
        actionButton.setVisibility(View.GONE);
    }

    @Override
    public void afterViewBind(View rootView, Bundle savedInstanceState) {
        initHomeFragment();
        initClickListener();
        initAnim(rgMainNavGroupChange, rgMainNavGroup);
    }

    private void initAnim(RadioGroup gone, RadioGroup show) {
        gone.clearAnimation();
        Animation btmAnimation = AnimationUtils.loadAnimation(this,
                R.anim.translate_live_answer_hide);
        gone.startAnimation(btmAnimation);
        gone.setVisibility(View.GONE);

        show.clearAnimation();
        Animation animation1 = AnimationUtils.loadAnimation(this,
                R.anim.translate_live_answer_show);
        show.startAnimation(animation1);
        show.setVisibility(View.VISIBLE);
    }

    private void initClickListener() {
        rbMainNavHome.setChecked(true);
        rgMainNavGroupChange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == mPresenter.getCurrentCheckedId()) {
                    return;
                }
                switch (checkedId) {
                    case R.id.rb_main_nav_home_beijing: {
                        mPresenter.replaceFragment(2);
                        break;
                    }
                    case R.id.rb_main_nav_car_source_shenzhen: {
                        mPresenter.replaceFragment(3);
                        break;
                    }
                }

            }
        });
        rgMainNavGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mPresenter.getCurrentCheckedId()) {
                    return;
                }
                switch (checkedId) {
                    case R.id.rb_main_nav_home: {
                        mPresenter.replaceFragment(0);
                        break;
                    }
                    case R.id.rb_main_nav_car_source: {
                        mPresenter.replaceFragment(1);
                        break;
                    }
                }
            }
        });
    }

    private void initHomeFragment() {
        mPresenter.initHomeFragmet();
    }


    @OnClick({R.id.fab_main_but, R.id.fab_main_but_})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_main_but:
                isChangeFragment = !isChangeFragment;
                if (isChangeFragment) {
                    initAnim(rgMainNavGroup, rgMainNavGroupChange);
                    handleChangeCheckPosition();
                } else {
                    initAnim(rgMainNavGroupChange, rgMainNavGroup);
                    handleCheckPosition();
                }
                break;
            case R.id.fab_main_but_:
                if (mPresenter.getShangHaiFragment() != null) {
                    mPresenter.getShangHaiFragment().close();
                }
                break;
        }
    }

    private void handleCheckPosition() {
        if (mPresenter.getCurrentFragmentIndex() > 1) {
            mPresenter.setCurrentFragmentIndex(0);
            rgMainNavGroupChange.clearCheck();
            rgMainNavGroup.check(rbMainNavHome.getId());
            mPresenter.setCurrentCheckedId(R.id.rb_main_nav_home);
        } else {
            mPresenter.replaceFragment(mPresenter.getCurrentFragmentIndex());
        }
    }

    private void handleChangeCheckPosition() {
        if (mPresenter.getCurrentFragmentIndex() < 2) {
            mPresenter.setCurrentFragmentIndex(2);
            rgMainNavGroup.clearCheck();
            rgMainNavGroupChange.check(rbMainNavHomeBeijing.getId());
            mPresenter.setCurrentCheckedId(R.id.rb_main_nav_home_beijing);
        } else {
            mPresenter.replaceFragment(mPresenter.getCurrentFragmentIndex());
        }
    }

    @Override
    public void addFragment(DaggerMvpFragment baseFragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_content, baseFragment).commit();
    }

    @Override
    public void showFragment(DaggerMvpFragment baseFragment) {
        getSupportFragmentManager().beginTransaction().show(baseFragment).commit();
    }

    @Override
    public void hideFragment(DaggerMvpFragment baseFragment) {
        getSupportFragmentManager().beginTransaction().hide(baseFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(200);
    }
}

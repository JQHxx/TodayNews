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
import com.news.today.todaynews.homesys.view.BeiJingFragment;
import com.news.today.todaynews.homesys.view.HangZhouFragment;
import com.news.today.todaynews.homesys.view.ShangHaiFragment;
import com.news.today.todaynews.homesys.view.ShenZhenFragment;

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


    private boolean isChangeFragment = false;
    private int currentFragmentIndex = 0;
    private DaggerMvpFragment[] mainFragments = new DaggerMvpFragment[4];
    private int currentCheckedId;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterViewBind(View rootView, Bundle savedInstanceState) {
        initHomeFragment();
        initClickListener();
        initAnim(rgMainNavGroupChange,rgMainNavGroup);
    }

    private void initAnim(RadioGroup gone,RadioGroup show) {
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
                if (checkedId == currentCheckedId) {
                    return;
                }
                switch (checkedId) {
                    case R.id.rb_main_nav_home_beijing: {
                        replaceFragment(2);
                        break;
                    }
                    case R.id.rb_main_nav_car_source_shenzhen: {
                        replaceFragment(3);
                        break;
                    }
                }

            }
        });
        rgMainNavGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == currentCheckedId) {
                    return;
                }
                switch (checkedId) {
                    case R.id.rb_main_nav_home: {
                        replaceFragment(0);
                        break;
                    }
                    case R.id.rb_main_nav_car_source: {
                        replaceFragment(1);
                        break;
                    }
                }
            }
        });
    }

    private void initHomeFragment() {
        currentFragmentIndex = 0;
        replaceFragment(currentFragmentIndex);
    }

    //替换Fragment
    private void replaceFragment(int index) {
        for (int i = 0; i < mainFragments.length; i++) {
            if (index != i) {
                if (mainFragments[i] != null) {
                    hideFragment(mainFragments[i]);
                }
            }
        }

        DaggerMvpFragment fragment = mainFragments[index];
        if (fragment != null) {
            addAndShowFragment(fragment);
            setCurChecked(index);
        } else {
            newFragmentAndSwitchAtIndex(index);
            setCurChecked(index);
        }
    }

    private void newFragmentAndSwitchAtIndex(int index) {
        DaggerMvpFragment baseFragment = null;
        switch (index) {
            case 0:
                baseFragment = ShangHaiFragment.newInstance();
                break;
            case 1:
                baseFragment = HangZhouFragment.newInstance();
                break;
            case 2:
                baseFragment = BeiJingFragment.newInstance();
                break;
            case 3:
                baseFragment = ShenZhenFragment.newInstance();
                break;
        }
        mainFragments[index] = baseFragment;
        addAndShowFragment(baseFragment);
    }


    //记录当前的点击位置
    private void setCurChecked(int index) {
        switch (index) {
            case 0: {
                currentFragmentIndex = 0;
                currentCheckedId = R.id.rb_main_nav_home;
            }
            break;
            case 1: {
                currentFragmentIndex = 1;
                currentCheckedId = R.id.rb_main_nav_car_source;
            }
            break;
            case 2: {
                currentFragmentIndex = 2;
                currentCheckedId = R.id.rb_main_nav_home_beijing;
            }
            break;
            case 3: {
                currentFragmentIndex = 3;
                currentCheckedId = R.id.rb_main_nav_car_source_shenzhen;
            }
            break;
        }
    }

    //展示Fragment
    private void addAndShowFragment(DaggerMvpFragment baseFragment) {
        if (!baseFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.fl_main_content, baseFragment).commit();
        } else if (!baseFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().show(baseFragment).commit();
        }
    }


    //隐藏Fragment
    private void hideFragment(DaggerMvpFragment baseFragment) {
        if (baseFragment == null) {
            return;
        }
        if (baseFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().hide(baseFragment).commit();
        }
    }

    @OnClick(R.id.fab_main_but)
    public void onClick(View view) {
        isChangeFragment = !isChangeFragment;
        if (isChangeFragment) {
            initAnim(rgMainNavGroup,rgMainNavGroupChange);
            handleChangeCheckPosition();
        } else {
            initAnim(rgMainNavGroupChange,rgMainNavGroup);
            handleCheckPosition();
        }

    }

    private void handleCheckPosition() {
        if (currentFragmentIndex > 1) {
            currentFragmentIndex = 0;
            rgMainNavGroupChange.clearCheck();
            rgMainNavGroup.check(rbMainNavHome.getId());
            currentCheckedId = R.id.rb_main_nav_home;
        } else {
            replaceFragment(currentFragmentIndex);
        }
    }

    private void handleChangeCheckPosition() {
        if (currentFragmentIndex < 2) {
            currentFragmentIndex = 2;
            rgMainNavGroup.clearCheck();
            rgMainNavGroupChange.check(rbMainNavHomeBeijing.getId());
            currentCheckedId = R.id.rb_main_nav_home_beijing;
        } else {
            replaceFragment(currentFragmentIndex);
        }
    }
}

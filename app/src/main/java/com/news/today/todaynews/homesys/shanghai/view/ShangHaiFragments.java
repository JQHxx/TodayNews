package com.news.today.todaynews.homesys.shanghai.view;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.news.today.todaynews.R;
import com.news.today.todaynews.base.DaggerMvpFragment;
import com.news.today.todaynews.homesys.home.view.MainActivity;
import com.news.today.todaynews.homesys.shanghai.lf.IShangHaiContract;
import com.news.today.todaynews.homesys.test.MostTextView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by anson on 2018/4/25.
 */

public class ShangHaiFragments extends DaggerMvpFragment implements IShangHaiContract.IView {

    @Inject
    IShangHaiContract.IPresenter mPresenter;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.content_vp)
    ViewPager contentVp;
    @BindView(R.id.tv_dec)
    MostTextView tvDec;
    @BindView(R.id.title_tv)
    TextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    public static ShangHaiFragments newInstance() {
        ShangHaiFragments homeFragment = new ShangHaiFragments();
        return homeFragment;
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_shanghai;
    }

    @Override
    public void afterViewBind(View rootView, Bundle savedInstanceState) {
        mPresenter.initFragments();
        initView();
    }

    public void close() {
        tvDec.close();
    }


    private void initView() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (appBarLayout.getHeight() + verticalOffset > getContext().getResources().getDisplayMetrics().heightPixels) {
                    ((MainActivity) getActivity()).show();
                } else {
                    ((MainActivity)getActivity()).hide();
                }
                if (appBarLayout.getHeight() + verticalOffset == mToolbar.getMeasuredHeight()) {
                    mTvTitle.setVisibility(View.VISIBLE);
                } else {
                    mTvTitle.setVisibility(View.GONE );
                }
            }
        });
        tvDec.setTextChangeListener(new MostTextView.OnTextChangeListener() {
            @Override
            public void shouqi() {
                ((MainActivity)getActivity()).hide();
            }

            @Override
            public void zhankai() {
                ((MainActivity)getActivity()).show();
            }
        });
        tvDec.setDesc(mPresenter.getDec(), TextView.BufferType.NORMAL);

        contentVp.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mPresenter.getFragments().get(position);
            }

            @Override
            public int getCount() {
                return mPresenter.getFragments().size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mPresenter.getTitles().get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
            }
        });

        contentVp.setOffscreenPageLimit(0);
        tab.setupWithViewPager(contentVp);
    }
}

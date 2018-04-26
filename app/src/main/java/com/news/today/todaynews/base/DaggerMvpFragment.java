package com.news.today.todaynews.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.news.today.mvp.base.BaseMvpFragment;
import com.news.today.task.helper.TaskHelper;
import com.news.today.task.lf.IGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by anson on 2018/4/25.
 */

public abstract class DaggerMvpFragment extends BaseMvpFragment implements IGroup, HasSupportFragmentInjector {


    private View rootView;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        doInject();
    }

    protected void doInject() {
        try {
            AndroidSupportInjection.inject(this);
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void bindView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingFragmentInjector;
    }

    @Override
    public String groupName() {
        return getClass().getName() + hashCode();
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            int rootLayoutId = getRootLayoutId();
            if (rootLayoutId > 0) {
                rootView = inflateView(getContext(), rootLayoutId);
            } else {
                throw new RuntimeException("rootLayoutId is < 0");
            }
            if (rootView != null) {
                try {
                    bindView(rootView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    afterViewBind(rootView, savedInstanceState);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new RuntimeException("rootView is null");
            }
        }
        return rootView;
    }

    public View inflateView(Context context, int rootLayoutId) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(rootLayoutId, null, false);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        TaskHelper.cancelGroup(groupName());

    }

    @Override
    public void onResume() {
        super.onResume();
        TaskHelper.onResume(groupName());
    }

    @Override
    public void onPause() {
        super.onPause();
        TaskHelper.onPause(groupName());
    }
}

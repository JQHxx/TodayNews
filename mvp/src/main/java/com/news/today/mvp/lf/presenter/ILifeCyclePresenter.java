package com.news.today.mvp.lf.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.news.today.mvp.lf.view.IMvpView;

/**
 * Created by anson on 2018/4/5.
 */

public interface ILifeCyclePresenter {

    void initParam(Bundle var1);

    void onCreate(Bundle var1, Bundle var2);

    void onViewCreate(Bundle var1, Bundle var2);

    void onViewDestroy();

    void onDestroy();

    void onPause();

    void onResume();

    void onSaveInstanceState(Bundle var1);

    void onStart();

    void onStop();

    void onNewIntent(Intent var1);

    void onActivityResult(int var1, int var2, Intent var3);

    void takeView(IMvpView var1);

    void destroyView();

}

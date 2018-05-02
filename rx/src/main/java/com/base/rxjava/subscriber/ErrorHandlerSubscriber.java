package com.base.rxjava.subscriber;

import android.content.Context;
import android.util.Log;

import com.base.rxjava.RxErrorHandler;
import com.base.rxjava.exception.BaseException;

import io.reactivex.disposables.Disposable;

/**
 * Created by WangHao
 *
 * @ 创建时间 2018/4/9  16:43
 */
public abstract  class ErrorHandlerSubscriber<T> extends DefualtSubscriber<T> {


    protected RxErrorHandler mErrorHandler = null;

    protected Context mContext;

    public ErrorHandlerSubscriber(Context context){
        this.mContext = context;

        mErrorHandler = new RxErrorHandler(mContext);

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        BaseException baseException =  mErrorHandler.handleError(e);

        if(baseException==null){
            e.printStackTrace();
            Log.d("ErrorHandlerSubscriber",e.getMessage());
        }
        else {

            mErrorHandler.showErrorMessage(baseException);
            if(baseException.getCode() == BaseException.ERROR_TOKEN){

            }

        }
    }
    @Override
    public void onComplete() {

    }




}

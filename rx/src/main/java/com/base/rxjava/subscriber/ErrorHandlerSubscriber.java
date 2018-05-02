package com.base.rxjava.subscriber;

import android.content.Context;

import com.base.rxjava.RxErrorHandler;

import io.reactivex.disposables.Disposable;


public abstract  class ErrorHandlerSubscriber<T> extends DefualtSubscriber<T> {


    protected RxErrorHandler mErrorHandler = null;

    protected Context mContext;

    public ErrorHandlerSubscriber(){//Context context
/*
        this.mContext = context;


        mErrorHandler = new RxErrorHandler(mContext);*/

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
      /*  BaseException baseException =  mErrorHandler.handleError(e);

        if(baseException==null){
            e.printStackTrace();
            Log.d("ErrorHandlerSubscriber",e.getMessage());
        }
        else {

            mErrorHandler.showErrorMessage(baseException);
            if(baseException.getCode() == BaseException.ERROR_TOKEN){

            }

        }*/
   //  e.printStackTrace();
    }
    @Override
    public void onComplete() {

    }




}

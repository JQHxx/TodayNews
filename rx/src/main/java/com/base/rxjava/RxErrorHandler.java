package com.base.rxjava;

import android.content.Context;
import android.widget.Toast;

import com.base.rxjava.exception.ApiException;
import com.base.rxjava.exception.BaseException;
import com.base.rxjava.exception.ErrorMessageFactory;

import java.net.SocketException;
import java.net.SocketTimeoutException;


/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.common.rx
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class RxErrorHandler {


    private Context mContext;

    public RxErrorHandler(Context context){

        this.mContext = context;
    }

    public BaseException handleError(Throwable e){

        BaseException exception = new BaseException();

        if(e instanceof ApiException){

            exception.setCode(((ApiException)e).getCode());

        }
        else  if(e instanceof SocketTimeoutException){

            exception.setCode(BaseException.SOCKET_TIMEOUT_ERROR);
        }
        else if(e instanceof SocketException){


        }
        else {

            exception.setCode(BaseException.UNKNOWN_ERROR);

        }

        exception.setDisplayMessage(ErrorMessageFactory.create(mContext,exception.getCode()));

        return  exception;
    }

    public void  showErrorMessage(BaseException e){


        Toast.makeText(mContext,e.getDisplayMessage(), Toast.LENGTH_LONG).show();

    }
}

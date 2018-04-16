package com.news.today.todaynews.system;


import android.support.annotation.CallSuper;
import android.text.TextUtils;

import com.news.today.http.parser.IResult;
import com.news.today.todaynews.R;
import com.news.today.todaynews.helper.ContextHelper;
import com.news.today.todaynews.system.control.IControl;
import com.news.today.todaynews.system.http.ResultCodes;
import com.news.today.todaynews.utils.Strings;


/**
 * @author Created by yh on 2016/4/28.
 */
public class ApiCallback<T> extends LfCallback<T> {
    private String msgPre = null;
    private static ISceneDispatch sceneDispatch;
    public ApiCallback() {
    }

    public ApiCallback(IControl control) {
        super.setControl(control);
    }


    public static void setSceneDispatch(ISceneDispatch sceneDispatch) {
        ApiCallback.sceneDispatch = sceneDispatch;
    }

    public int getMsgId() {
        return 0;
    }

    public String getMsgPre() {
        if (msgPre == null) {
            int msgId = getMsgId();
            if (msgId != 0) {
                msgPre = ContextHelper.getString(msgId);
            } else {
                msgPre = Strings.EMPTY;
            }
        }
        return msgPre;
    }

    @Override
    protected final void doSuccess(IResult<T> result) {
        super.doSuccess(result);
    }

    @Override
    public final void doFailure(IResult result) {
        showCommonError(result);
        super.doFailure(result);
    }

    @Override
    public void onSuccess(IResult<T> result) {
    }

    @Override
    public boolean onFailure(IResult result) {
        return false;
    }

    public void showCommonError(IResult result) {
        String code = result.code();
        String msgPre = getMsgPre();
        boolean isEmptyMsgPre = TextUtils.isEmpty(msgPre);
        switch (code) {
            case ResultCodes.CODE_DATA_ERROR:
                //数据异常
                showErrorScene(InfoScene.serviceError, msgPre);
                if (!isEmptyMsgPre) {
                    KklToast.showShort(msgPre.concat(ContextHelper.getString(R.string.data_error)));
                }
                break;
            case ResultCodes.CODE_ERROR_DATA_MISS:
                showErrorScene(InfoScene.serviceError, msgPre);
                //数据缺失
                if (!isEmptyMsgPre) {
                    KklToast.showShort(msgPre.concat(ContextHelper.getString(R.string.data_lost)));
                }
                break;
            case ResultCodes.CODE_NETGATE_ERROR: {
                showErrorScene(InfoScene.serviceError, msgPre);
                //网关异常
                String dataCode = (String) result.data();
                if (TextUtils.equals(RemoteResultCodes.LOGIN_OTHER_PLACE, dataCode)) {
                    KklToast.showShort(ContextHelper.getString(R.string.account_error_3));
                    UserHelper.logout(true);
                    return;
                } else if (!isEmptyMsgPre) {
                    KklToast.showShort(msgPre.concat(ContextHelper.getString(R.string.net_work_error)));
                }
                break;
            }
            case ResultCodes.CODE_SERVER_ERROR: {
                showErrorScene(InfoScene.serviceError, msgPre);
                //服务器端返回success为false的情况
                String dataCode = (String) result.data();
                if (TextUtils.equals(RemoteResultCodes.LOGIN_OTHER_PLACE, dataCode)) {
                    EventHelper.post(new ReLoginEvent());
                }
                if (!isEmptyMsgPre) {
                    KklToast.showShort(result.msg());
                }
                break;
            }
            case ResultCodes.CODE_EXCEPTION: {
                //exception不做任何事情
                break;
            }
            default:
                showErrorScene(InfoScene.serviceError, msgPre);
                break;
        }

    }

    @Override
    @CallSuper
    public void onException(Throwable t) {
        if (sceneDispatch != null) {
            String scene = sceneDispatch.routeToScene(t);
            switch (scene) {
                case InfoScene.loginByOtherClient:
                    showLogin("你的账号已经在其他终端登录，电脑和手机无法同时登录，请检查后重新登录");
                    break;
                case InfoScene.sessionTimeout:
                    showLogin("登录信息失效，请重新登录");
                    break;
                default:
                    showErrorScene(scene, t);
            }
        }
        onFailure(Result.fail(t));
    }


    private void showLogin(String msg) {
        synchronized (ApiCallback.class) {
            boolean isLogin = UserHelper.isLogin();
            if (!isLogin) {
                UserHelper.logout(true);//清空本地的登录信息
                KklToast.showShort(msg);
            }
        }
    }

    @CallSuper
    @Override
    public void onCancelled() {
        onFailure(Result.canceled());
    }


}
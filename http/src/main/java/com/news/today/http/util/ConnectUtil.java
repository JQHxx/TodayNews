package com.news.today.http.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by anson on 2018/4/15.
 */

public class ConnectUtil {
    /**
     * 判断当前有没有网络连接
     */
    public static boolean getNetworkState(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            return !(networkinfo == null || !networkinfo.isAvailable());
        }
        return false;
    }

    /**
     * 获取当前手机网络类型
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();

        String typeName;
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                typeName = "WIFI";
            } else {
                int subType = info.getSubtype();
                int type = NetworkUtil.getNetworkClass(subType);
                switch (type) {
                    case 1:
                        typeName = "2G";
                        break;
                    case 2:
                        typeName = "3G";
                        break;
                    case 3:
                        typeName = "4G";
                        break;
                    default:
                        typeName = "UNKNOW";
                        break;
                }
            }
        } else {
            typeName = "None";
        }

        return typeName;
    }
}

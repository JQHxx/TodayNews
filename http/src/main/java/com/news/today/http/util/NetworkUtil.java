package com.news.today.http.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 */
public final class NetworkUtil {

    public static final int NETWORK_TYPE_EHRPD = 14; // Level 11
    public static final int NETWORK_TYPE_EVDO_B = 12; // Level 9
    public static final int NETWORK_TYPE_HSPAP = 15; // Level 13
    public static final int NETWORK_TYPE_IDEN = 11; // Level 8
    public static final int NETWORK_TYPE_LTE = 13; // Level 11

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context
     * @return
     */
    public static boolean isFast(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && isFast(info.getType(), info.getSubtype()));
    }

    public static boolean isWIFI(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && ConnectivityManager.TYPE_WIFI == info.getType());
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return
     */
    public static boolean isFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {

            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                // NOT AVAILABLE YET IN API LEVEL 7
                case NETWORK_TYPE_EHRPD:
                    return true; // ~ 1-2 Mbps
                case NETWORK_TYPE_EVDO_B:
                    return true; // ~ 5 Mbps
                case NETWORK_TYPE_HSPAP:
                    return true; // ~ 10-20 Mbps
                case NETWORK_TYPE_IDEN:
                    return false; // ~25 kbps
                case NETWORK_TYPE_LTE:
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取网络连接的类型，如果网络未连接返回null
     *
     * @param context
     * @return
     */
    public static String getNetworkName(Context context) {
        if (isConnected(context)) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
                return info.getTypeName();
            } else {
                return info.getSubtypeName();
            }
        } else {
            return null;
        }
    }


    public static String getNetworkOperator(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkOperator();
    }



    /**
     * Return general class of network type, such as "3G" or "4G". In cases
     * where classification is contentious, this method is conservative.
     *
     * @hide
     */
    public static int getNetworkClass(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case 16://NETWORK_TYPE_GSM
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 1;//NETWORK_CLASS_2_G
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
            case 17://NETWORK_TYPE_TD_SCDMA
                return 2;//NETWORK_CLASS_3_G
            case NETWORK_TYPE_LTE:
            case 18://NETWORK_TYPE_IWLAN
                return 3;//NETWORK_CLASS_4_G
            default:
                return 0;//NETWORK_CLASS_UNKNOWN
        }
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return {@link ConnectivityManager#TYPE_WIFI}或者
     * {@link ConnectivityManager#TYPE_MOBILE}
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) return -1;
        return activeNetworkInfo.getType();
    }

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
    public static String getNetworkTypeString(Context context) {
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

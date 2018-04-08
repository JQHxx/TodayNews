package com.news.today.task.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

/**
 * Created by anson on 2018/4/6.
 */

public class ThreadUtil {

    private final static Handler MAIN = new Handler(Looper.getMainLooper());

    /**
     * 是否主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {
        String processName = getProgressName(context);
        String packageName = context.getPackageName();
        boolean isMainProcess = TextUtils.equals(processName, packageName);
        return isMainProcess;
    }

    /**
     * 是否ui线程
     *
     * @return
     */
    public static boolean isUiThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 子线程中向ui线程抛事件
     *
     * @param runnable
     */
    public static void postUi(final Runnable runnable) {
        MAIN.post(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    /**
     * 延迟执行
     *
     * @param runnable
     * @param delayMillis
     */
    public static void postDelayed(final Runnable runnable, long delayMillis) {
        MAIN.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception throwable) {
                    throwable.printStackTrace();
                }
            }
        }, delayMillis);
    }

    /**
     * 取消执行runnable
     *
     * @param runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        MAIN.removeCallbacks(runnable);
    }


    /**
     * 取得当前进程的名称
     *
     * @param context
     * @return
     */
    public static String getProgressName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}

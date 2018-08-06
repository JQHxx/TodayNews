package com.news.today.todaynews.edgesys.rx;

/**
 * Created by WangHao
 *
 * @ 创建时间 2018/5/2  16:59
 */

public class Rxutils {
    private static Rxutils rxutils;

    public static Rxutils getInstance() {
        if (rxutils == null) {
            synchronized (Rxutils.class) {
                if (rxutils == null) {
                    rxutils = new Rxutils();
                }
            }
        }
        return rxutils;
    }

    public Rxutils getParam(){
        return this;
    }
}

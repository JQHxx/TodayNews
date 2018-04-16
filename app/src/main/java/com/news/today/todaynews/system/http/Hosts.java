package com.news.today.todaynews.system.http;


import com.news.today.http.api.IHost;

/**
 * 服务器host的定义
 * Created by ye on 2017/10/11.
 */

public interface Hosts {

    IHost jhData = new IHost() {
        @Override
        public String getHost() {
            return "http://v.juhe.cn/joke/";
        }

        @Override
        public String getDefaultPath() {
            return "randJoke.php";
        }
    };

}

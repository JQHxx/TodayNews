package com.news.today.todaynews.system.http;


import com.news.today.http.api.IHost;

/**
 * Created by anson on 2018/4/15.
 */

public interface Hosts {

    IHost jhData = new IHost() {
        @Override
        public String getHost() {
            return "http://v.juhe.cn/joke/content/";
        }

        @Override
        public String getDefaultPath() {
            return "randJoke.php";
        }
    };

}

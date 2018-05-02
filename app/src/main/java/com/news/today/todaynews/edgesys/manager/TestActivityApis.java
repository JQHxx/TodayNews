package com.news.today.todaynews.edgesys.manager;

import com.news.today.http.annotation.RequestMethod;
import com.news.today.http.api.IApi;
import com.news.today.todaynews.edgesys.entity.XiaoHua;
import com.news.today.todaynews.system.http.JHApi;

/**
 * Created by anson on 2018/4/15.
 */

public interface TestActivityApis {
    IApi getXiaoHuaList = JHApi.sendHttp("list.php", RequestMethod.Get,XiaoHua.class);
}

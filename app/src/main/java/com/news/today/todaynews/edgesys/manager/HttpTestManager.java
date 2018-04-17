package com.news.today.todaynews.edgesys.manager;

import com.news.today.http.parser.IResult;
import com.news.today.http.LfManager;
import com.news.today.todaynews.helper.ContextHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by anson on 2018/4/15.
 */

@Singleton
public class HttpTestManager extends LfManager{

    @Inject
    public HttpTestManager() {

    }
    //http://v.juhe.cn/joke/list.php?time=1523948972&sort=desc&key=bbc57dd5e4f05991aff09eafd2e667e0&page=1&pagesize=10
    public IResult getXiaoHuaList(int currentPage) {
        Map<String, Object> params = new HashMap<>();
        params.put("sort","desc");
        params.put("page",currentPage);
        params.put("pagesize",10);
        params.put("time",System.currentTimeMillis()/1000);
        params.put("key","bbc57dd5e4f05991aff09eafd2e667e0");
        return super.execute(ContextHelper.getAppContext(),TestActivityApis.getXiaoHuaList, params);
    }

}

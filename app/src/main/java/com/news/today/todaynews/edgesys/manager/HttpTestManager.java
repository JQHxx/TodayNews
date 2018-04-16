package com.news.today.todaynews.edgesys.manager;

import com.news.today.http.parser.IResult;
import com.news.today.http.LfManager;

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

    public IResult getXiaoHuaList(int currentPage) {
        Map<String, String> params = new HashMap<>();
        return super.execute(TestActivityApis.getXiaoHuaList, params);
    }

}

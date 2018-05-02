package com.news.today.todaynews.edgesys.manager;

import com.news.today.http.LfManager;
import com.news.today.todaynews.helper.ContextHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by anson on 2018/4/15.
 */

@Singleton
public class HttpTestManager extends LfManager {

    @Inject
    public HttpTestManager() {

    }

    //http://v.juhe.cn/joke/content/list.php?time=1523948972&sort=desc&key=bbc57dd5e4f05991aff09eafd2e667e0&page=1&pagesize=10
    public Observable getXiaoHuaOb(final int currentPage) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) {
                Map<String, Object> params = new HashMap<>();
                params.put("sort", "desc");
                params.put("page", currentPage);
                params.put("pagesize", 10);
                params.put("time", System.currentTimeMillis() / 1000);
                params.put("key", "bbc57dd5e4f05991aff09eafd2e667e0");
                e.onNext(execute(ContextHelper.getAppContext(), TestActivityApis.getXiaoHuaList, params));
            }
        });
    }

}

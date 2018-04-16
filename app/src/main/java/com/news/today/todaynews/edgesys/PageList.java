package com.news.today.todaynews.edgesys;

import android.support.annotation.Keep;
import android.text.TextUtils;

import com.news.today.todaynews.IPage;

import java.util.List;

/**
 * Created by ye on 2017/12/28.
 */
@Keep
public class PageList<T> implements IPage<T> {
    public static <T> PageList<T> makePagedList(List<T> data, boolean hasNext) {
        PageList<T> pagedList = new PageList<>();
        pagedList.resultList = data;
        pagedList.hasNext = hasNext;
        return pagedList;
    }

    public static <T> PageList<T> makePagedList(boolean hasNext) {
        PageList<T> pagedList = new PageList<>();
        pagedList.hasNext = hasNext;
        return pagedList;
    }

    private Boolean hasNext;


    public String currentPage;
    public String itemsPerPage;
    public List<T> resultList;
    public String totalItems;
    public String totalPages;


    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<T> getList() {
        return resultList;
    }

    @Override
    public boolean hasNext() {
        if (hasNext == null) {
            hasNext = !TextUtils.equals(currentPage, totalPages);
        }
        return hasNext;
    }

}

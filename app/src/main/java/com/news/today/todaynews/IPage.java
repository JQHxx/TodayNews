package com.news.today.todaynews;

import java.util.List;

/**
 * Created by anson on 2018/4/8.
 */
public interface IPage<T> {
    /**
     * 取得内容数据
     *
     * @return
     */
    List<? extends T> getList();

    /**
     * 是否已经到了最后
     *
     * @return
     */
    boolean hasNext();
}
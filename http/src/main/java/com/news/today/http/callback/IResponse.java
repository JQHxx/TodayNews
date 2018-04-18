package com.news.today.http.callback;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by anson on 2018/4/8.
 */
public interface IResponse {
    boolean isSuccess();

    String getHeader(String key, String defaultValue);

    String getBody();

    InputStream getInputStream() throws IOException;

    /**
     * 取得数据的总长度
     *
     * @return
     */
    long getTotalLength();

    /**
     * 置空内部一些属性，释放内存
     */
    void destroy();
}

package com.news.today.todaynews.system.http;

/**
 * 全局的ResultCode定义，若不是全局请不要放在这里
 * Created by ye on 2017/11/21.
 */
public interface ResultCodes {
    /**
     * 异常code
     */
    String CODE_EXCEPTION = "_CODE_EXCEPTION";

    /**
     * 未定义code错误
     */
    String CODE_UNKNOW = "_CODE_UNKNOW";

    /**
     * 任务被取消
     */
    String CODE_TASK_CANCELED = "_CODE_TASK_CANCELED";

    String CODE_TASK_EXCEPTION = "_CODE_TASK_EXCEPTION";

    /**
     * 网络错误
     */
    String CODE_NETWORKERROR = "_CODE_NETWORKERROR";

    /**
     * 返回的主体内容为空
     */
    String CODE_DATA_EMPTY_ERROR = "_CODE_DATA_EMPTY_ERROR";
    /**
     * 解析出了异常
     */
    String CODE_PARSE_ERROR = "_CODE_PARSE_ERROR";
    /**
     * 数据异常
     */
    String CODE_DATA_ERROR = "_CODE_CODE_DATA_ERROR";
    /**
     * 数据缺失
     */
    String CODE_ERROR_DATA_MISS = "_CODE_ERROR_DATA_MISS";
    /**
     * 网关返回错误
     */
    String CODE_NETGATE_ERROR = "_CODE_NETGATE_ERROR";
    /**
     * 服务器异常
     */
    String CODE_SERVER_ERROR = "_CODE_SERVER_ERROR";
    /**
     * 服务器端返回成功的code
     */
    String CODE_SUCCESS = "_CODE_SUCCESS";

}

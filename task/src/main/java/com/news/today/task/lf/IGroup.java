package com.news.today.task.lf;

/**
 * Created by anson on 2018/4/6.
 */
public interface IGroup {
    /**
     * 默认分组名
     */
    String DEFAULT_GROUP_NAME = "dg";//default-group
    /**
     * 默认分组
     */
    IGroup defaultGroup = new IGroup() {
        @Override
        public String groupName() {
            return DEFAULT_GROUP_NAME;
        }
    };


    /**
     * 分组名称
     *
     * @return
     */
    String groupName();

}

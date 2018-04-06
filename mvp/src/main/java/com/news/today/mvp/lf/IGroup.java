package com.news.today.mvp.lf;

/**
 * Created by anson on 2018/4/5.
 */

public interface IGroup {
    String DEFAULT_GROUP_NAME = "dg";
    IGroup defaultGroup = new IGroup() {
        public String groupName() {
            return "dg";
        }
    };

    String groupName();
}

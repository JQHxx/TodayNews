package com.news.today.todaynews.system;

import com.news.today.task.lf.ITaskBackground;
import com.news.today.task.lf.ITaskCallback;

/**
 * Created by anson on 2018/4/8.
 */


public abstract class AbsTask<T,Result> implements ITaskBackground<Result>,ITaskCallback<Result>{

}

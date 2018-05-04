package com.news.today.todaynews.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by anson on 2018/5/2.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScope {
}

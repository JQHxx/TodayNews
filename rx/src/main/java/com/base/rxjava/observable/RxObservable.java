package com.base.rxjava.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

/**
 * Created by WangHao
 *
 * @ 创建时间 2018/5/2  15:07
 */

public class RxObservable<T> extends Observable<T> {


    @Override
    protected void subscribeActual(Observer observer) {
    }

    public static <T> Observable<T> creats(ObservableOnSubscribe<T> source) {
        return  Observable.create(source);
    }


}

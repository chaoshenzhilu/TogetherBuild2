package com.bwf.togetherbuild.utils.rxjava;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/14 0014.
 */

public class RxJavaUtil {
    public void testRxJava() {
        Observable switcher= Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("On");
                subscriber.onNext("Off");
                subscriber.onNext("On");
                subscriber.onNext("On");
                subscriber.onCompleted();
            }
        });
    }
}

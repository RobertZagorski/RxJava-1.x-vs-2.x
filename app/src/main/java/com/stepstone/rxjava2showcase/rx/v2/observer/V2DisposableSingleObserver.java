package com.stepstone.rxjava2showcase.rx.v2.observer;

import com.stepstone.rxjava2showcase.util.Logger;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 16/03/2017.
 * @see <a href="http://reactivex.io/RxJava/javadoc/io/reactivex/observers/DisposableSingleObserver.html">DisposableSingleObserver</a>
 */

public class V2DisposableSingleObserver<T> extends DisposableSingleObserver<T> {

    /*@Override
    public void onComplete() {
        Log.d("V2DisposableSingle", "OnComplete");
    }*/

    @Override
    public void onSuccess(T t) {
        Logger.d("V2DisposableSingle", "OnNext: " + (t != null ? t.toString() : "null"));
    }

    @Override
    public void onError(Throwable e) {
        Logger.d("V2DisposableSingle", "OnError: " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }
}

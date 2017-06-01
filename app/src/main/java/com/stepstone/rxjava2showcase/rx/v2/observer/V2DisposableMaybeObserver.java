package com.stepstone.rxjava2showcase.rx.v2.observer;

import com.stepstone.rxjava2showcase.util.Logger;

import io.reactivex.observers.DisposableMaybeObserver;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 16/03/2017.
 * @see <a href="http://reactivex.io/RxJava/javadoc/io/reactivex/observers/DisposableMaybeObserver.html">DisposableMaybeObserver</a>
 */

public class V2DisposableMaybeObserver<T> extends DisposableMaybeObserver<T> {

    @Override
    public void onComplete() {
        Logger.d("V2DisposableMaybe", "OnComplete");
    }

    @Override
    public void onSuccess(T t) {
        Logger.d("V2DisposableMaybe", "OnNext: " + (t != null ? t.toString() : "null"));
    }

    @Override
    public void onError(Throwable e) {
        Logger.d("V2DisposableMaybe", "OnError: " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }
}

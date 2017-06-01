package com.stepstone.rxjava2showcase.rx.v2.observer;

import com.stepstone.rxjava2showcase.util.Logger;

import io.reactivex.observers.DisposableCompletableObserver;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 16/03/2017.
 * @see <a href="http://reactivex.io/RxJava/javadoc/io/reactivex/observers/DisposableCompletableObserver.html">DisposableCompletableObserver</a>
 */
public class V2DisposableCompletableObserver extends DisposableCompletableObserver {

    @Override
    public void onComplete() {
        Logger.d("V2DisposableCompletable", "OnComplete");
    }

    @Override
    public void onError(Throwable e) {
        Logger.d("V2DisposableCompletable", "OnError: " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }
}

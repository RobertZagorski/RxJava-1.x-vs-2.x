package com.stepstone.rxjava2showcase.rx.v2.observer;

import com.stepstone.rxjava2showcase.util.Logger;

import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;

/**
 * * Base {@link io.reactivex.Observer} for {@link io.reactivex.Observable}.
 * Notice {@code extends} {@link DefaultObserver}
 *
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 10/03/2017.
 * @see <a href="http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/observers/DisposableObserver.html">DisposableObserver</a>
 */

public class V2Observer<T> extends DisposableObserver<T> {
    @Override
    public void onComplete() {
        Logger.d("V2Observer", "OnCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Logger.d("V2Observer", "OnError: " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }

    @Override
    public void onNext(T t) {
        Logger.d("V2Observer", "OnNext: " + (t != null ? t.toString() : "null"));
    }
}

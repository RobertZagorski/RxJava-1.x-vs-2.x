package com.stepstone.rxjava2showcase.rx.v1;

import android.util.Log;

import rx.Subscriber;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 10/03/2017.
 */

public class V1Subscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        Log.d("ObservableV1", "OnCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.d("ObservableV1", "OnError: " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }

    @Override
    public void onNext(T t) {
        Log.d("ObservableV1", "OnNext: " + (t != null ? t.toString() : "null"));
    }
}

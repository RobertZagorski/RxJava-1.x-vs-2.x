package com.stepstone.rxjava2showcase.rx.v2.subscriber;

import com.stepstone.rxjava2showcase.util.Logger;

import io.reactivex.subscribers.DefaultSubscriber;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Base {@link org.reactivestreams.Subscriber} for {@link io.reactivex.Flowable}.
 * Notice {@link extends} {@link DefaultSubscriber}
 *
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 10/03/2017.
 * @see <a href="http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/subscribers/DefaultSubscriber.html">DefaultSubscriber</a>
 */

public class V2Subscriber<T> extends DisposableSubscriber<T> {

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("ObservableV2", "onStart of Flowable");
    }

    @Override
    public void onComplete() {
        Logger.d("ObservableV2", "OnCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Logger.e("ObservableV2", "OnError:", e);
    }

    @Override
    public void onNext(T t) {
        Logger.d("ObservableV2", "OnNext: " + (t != null ? t.toString() : "null"));
    }
}

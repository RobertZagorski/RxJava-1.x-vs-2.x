package com.stepstone.rxjava2showcase.rx.v2.consumer;

import com.stepstone.rxjava2showcase.util.Logger;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 16/03/2017.
 * @see <a href="http://reactivex.io/RxJava/javadoc/io/reactivex/functions/Consumer.html">Consumer</a>
 */

public class V2Consumer<T> implements Consumer<T> {
    @Override
    public void accept(@NonNull T t) throws Exception {
        Logger.d("V2Consumer", "Accept: " + t.toString());
    }
}

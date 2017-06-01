package com.stepstone.rxjava2showcase.rx.v1;

import android.util.Log;

import com.stepstone.rxjava2showcase.util.Logger;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.PublishSubject;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 10/03/2017.
 */

public class ObservableV1Examples {

    public static void invokeNull() {
        Observable.just(1)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return null;
                    }
                })
                .subscribe(new V1Subscriber<Integer>());
    }

    public static void invokeNullInOnNext() {
        Observable.just(1)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return null;
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d("ObservableV1", "OnCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ObservableV1", "OnError: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        throw new NullPointerException("Null in onNext");
                    }
                });
    }

    public static void invokeSubject() {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.subscribe(new V1Subscriber<Integer>());
        subject.onNext(1);
    }

    public static void invokeAdvanced() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        getAdvancedObservable(publishSubject)
                .subscribe(new V1Subscriber<String>());
        publishSubject.onNext(1);
    }

    public static Observable<String> getAdvancedObservable(PublishSubject<Integer> publishSubject) {
        return publishSubject
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer;
                    }
                })
                .flatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        return Observable.just(String.valueOf(integer));
                    }
                })
                .zipWith(Observable.just(1L), new Func2<String, Long, Long>() {
                    @Override
                    public Long call(String firstNumber, Long secondNumber) {
                        return Long.valueOf(firstNumber + String.valueOf(secondNumber));
                    }
                })
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long number) {
                        Logger.d("V1Advanced", number.toString());
                    }
                })
                .flatMap(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long number) {
                        String[] list = String.valueOf(number).split("/(./)");
                        return Observable.from(list);
                    }
                })
                // doAfterNext non existent
                .take(1)
                .toList()
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        String number = strings.get(0);
                        return Observable.just(number + number);
                    }
                });
    }
}

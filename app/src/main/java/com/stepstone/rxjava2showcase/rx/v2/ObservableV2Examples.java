package com.stepstone.rxjava2showcase.rx.v2;

import com.stepstone.rxjava2showcase.rx.v2.consumer.V2Consumer;
import com.stepstone.rxjava2showcase.rx.v2.observer.V2DisposableCompletableObserver;
import com.stepstone.rxjava2showcase.rx.v2.observer.V2DisposableMaybeObserver;
import com.stepstone.rxjava2showcase.rx.v2.observer.V2DisposableSingleObserver;
import com.stepstone.rxjava2showcase.rx.v2.observer.V2Observer;
import com.stepstone.rxjava2showcase.rx.v2.subscriber.V2Subscriber;
import com.stepstone.rxjava2showcase.util.Logger;

import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 10/03/2017.
 */

public class ObservableV2Examples {

    public static void invokeNull() {
        Observable.just(1)
                .map(new Function<Integer, Object>() {
                    @Override
                    public Integer apply(Integer integer) {
                        return null;
                    }
                })
                .subscribe(new V2Observer<>());
        Flowable.just(1)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        return null;
                    }
                })
                .subscribe(new V2Subscriber<Integer>());
    }

    /**
     * @see <a href="http://reactivex.io/RxJava/javadoc/io/reactivex/Maybe.html>Maybe</a>
     */
    public static void invokeMaybe() {
        DisposableMaybeObserver<Integer> disposableMaybeObserver = new V2DisposableMaybeObserver<>();
        Maybe.just(1)
                .subscribe(disposableMaybeObserver);

        disposableMaybeObserver = new V2DisposableMaybeObserver<>();
        Maybe.<Integer>empty()
                .subscribe(disposableMaybeObserver);
    }

    /**
     * @see <a href="http://reactivex.io/RxJava/javadoc/io/reactivex/Single.html">Single</a>
     */
    public static void invokeSingle() {
        DisposableSingleObserver<Integer> disposableSingleObserver = new V2DisposableSingleObserver<>();
        Single.just(1)
                .subscribe(disposableSingleObserver);
    }

    public static void invokeCompletable() {
        DisposableCompletableObserver disposableCompletableObserver = new V2DisposableCompletableObserver();
        Completable completable = Completable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return null;
            }
        });
        completable.subscribe(disposableCompletableObserver);
    }

    /**
     * @see <a href="http://reactivex.io/RxJava/javadoc/io/reactivex/subjects/PublishSubject.html">PublishSubject</a>
     */
    public static void invokeSubject() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject.subscribe(new V2Observer<Integer>());
        publishSubject.subscribe(new V2Consumer<Integer>());
        publishSubject.onNext(1);
    }

    /**
     * @see <a href="http://reactivex.io/RxJava/javadoc/io/reactivex/processors/PublishProcessor.html">PublishProcessor</a>
     */
    public static void invokeProcessor() {
        PublishProcessor<Integer> publishProcessor = PublishProcessor.create();
        publishProcessor
                .onBackpressureLatest()
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        Thread.sleep(25);
                        return integer;
                    }
                })
                .subscribe(new V2Subscriber<Integer>());
        publishProcessor.onNext(1);
        publishProcessor.onNext(2);
        publishProcessor.onNext(3);
    }

    public static void invokeAdvanced() {
        PublishProcessor<Integer> publishProcessor = PublishProcessor.create();
        getAdvancedObservable(publishProcessor)
                .subscribe(new V2Subscriber<String>());
        publishProcessor.onNext(1);
    }

    public static Flowable<String> getAdvancedObservable(Processor<Integer, Integer> publishProcessor) {
        return Flowable.<Integer>fromPublisher(publishProcessor)
                .map(new Function<Integer, Integer>() { //Func1 -> Function
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception { //call(T t) -> apply(T t)
                        return integer;
                    }
                })
                .flatMap(new Function<Integer, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(@NonNull Integer integer) throws Exception { // return type -> Flowable implements Publisher
                        return Flowable.just(String.valueOf(integer));
                    }
                })
                .zipWith(Flowable.just(1L), new BiFunction<String, Long, Long>() { // Func2 -> BiFunction
                    @Override
                    public Long apply(@NonNull String firstNumber, @NonNull Long secondNumber) throws Exception {
                        return Long.valueOf(firstNumber + String.valueOf(secondNumber));
                    }
                })
                .doOnNext(new Consumer<Long>() { // Action1 -> Consumer
                    @Override
                    public void accept(@NonNull Long number) throws Exception { //call(T t) -> accept(T t)
                        Logger.d("V2Advanced_doOnNext", number.toString());
                    }
                })
                .flatMap(new Function<Long, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(@NonNull Long number) throws Exception {
                        String[] list = String.valueOf(number).split("");
                        return Flowable.fromArray(list); //Observable.from() -> Flowable.fromArray();
                    }
                })
                .doAfterNext(new Consumer<String>() { // NEW
                    @Override
                    public void accept(@NonNull String string) throws Exception {
                        Logger.d("V2Advanced_doAfterNext", string);
                    }
                })
                .take(1)
                .toList() // returns Single
                .flatMap(new Function<List<String>, SingleSource<String>>() { // Observable -> SingleSource
                    @Override
                    public SingleSource<String> apply(@NonNull List<String> stringList) throws Exception {
                        String number = stringList.get(0);
                        return Single.just(number + number);
                    }
                })
                .toFlowable()
                /*.compose(new FlowableTransformer<String, String>() {
                    @Override
                    public Publisher<String> apply(Flowable<String> upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.trampoline()); // immediate() -> trampoline()
                    }
                })*/
                .flatMap(new Function<String, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(@NonNull String number) throws Exception {
                        List<String> list = Arrays.asList(number.split(""));
                        return Flowable.fromIterable(list); //Observable.from() -> Flowable.fromIterable();
                    }
                })
                .skip(1);
    }
}

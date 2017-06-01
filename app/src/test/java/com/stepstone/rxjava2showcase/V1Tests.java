package com.stepstone.rxjava2showcase;

import com.stepstone.rxjava2showcase.rx.v1.ObservableV1Examples;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;
import rx.subjects.PublishSubject;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 04/05/2017.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class V1Tests {
    private static final int DEBOUNCE_TIME = 300;

    TestSubscriber<String> testSubscriber;
    Observable<String> observable;
    PublishSubject<Integer> publishSubject;

    @Before
    public void setUp() throws Exception {
        publishSubject = PublishSubject.create();
        observable = ObservableV1Examples.getAdvancedObservable(publishSubject);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void testValue() throws Exception {
        observable.subscribe(testSubscriber);
        publishSubject.onNext(1);

        testSubscriber.assertValueCount(1);
    }

    @Test
    public void testError() throws Exception {
        observable
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        throw new NullPointerException("null in test");
                    }
                }).subscribe(testSubscriber);
        publishSubject.onNext(1);

        testSubscriber.assertError(NullPointerException.class);
    }

    @Test
    public void testTestScheduler() throws Exception {
        TestScheduler testScheduler = Schedulers.test();
        observable
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .subscribe(testSubscriber);

        testScheduler.triggerActions();
        publishSubject.onNext(1);

        testSubscriber.assertNoValues();

        testScheduler.advanceTimeBy(DEBOUNCE_TIME, TimeUnit.MILLISECONDS);
        testSubscriber.assertValueCount(1);
    }
}

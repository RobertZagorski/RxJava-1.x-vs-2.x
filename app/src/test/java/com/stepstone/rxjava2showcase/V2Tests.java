package com.stepstone.rxjava2showcase;

import com.stepstone.rxjava2showcase.rx.v2.ObservableV2Examples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.processors.ReplayProcessor;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 04/05/2017.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class V2Tests {
    private static final int DEBOUNCE_TIME = 300;

    Flowable<String> flowable;
    ReplayProcessor<Integer> replayProcessor;
    TestSubscriber<String> testSubscriber;

    @Before
    public void setUp() throws Exception {
        replayProcessor = ReplayProcessor.create();
        flowable = ObservableV2Examples.getAdvancedObservable(replayProcessor);
    }

    @After
    public void tearDown() throws Exception {
        testSubscriber.dispose();
        replayProcessor.onComplete();
    }

    @Test
    public void testValue() throws Exception {
        testSubscriber = flowable
                .test();
        replayProcessor.onNext(1);
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void testError() throws Exception {
        testSubscriber = flowable
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        throw new NullPointerException("null in test");
                    }
                })
                .test();
        replayProcessor.onNext(1);
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertError(NullPointerException.class);
    }

    @Test
    public void testTestScheduler() throws Exception {
        TestScheduler testScheduler = new TestScheduler();
        testSubscriber = flowable
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .subscribeOn(testScheduler)
                .test();
        testScheduler.triggerActions();
        replayProcessor.onNext(1);

        testSubscriber.assertNoValues();

        testScheduler.advanceTimeBy(DEBOUNCE_TIME, TimeUnit.MILLISECONDS);
        testSubscriber.assertValueCount(1);
    }
}

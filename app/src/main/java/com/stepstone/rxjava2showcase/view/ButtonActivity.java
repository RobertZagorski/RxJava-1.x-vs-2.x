package com.stepstone.rxjava2showcase.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stepstone.rxjava2showcase.R;
import com.stepstone.rxjava2showcase.rx.v1.ObservableV1Examples;
import com.stepstone.rxjava2showcase.rx.v2.ObservableV2Examples;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ButtonActivity extends AppCompatActivity {

    @BindView(R.id.nulls)
    RxCompareView rcvNulls;

    @BindView(R.id.new_types)
    RxCompareView rcvNewTypes;

    @BindView(R.id.new_subject_processors)
    RxCompareView rcvNewProcessor;

    @BindView(R.id.advanced_example)
    RxCompareView rcvAdvancedExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        ButterKnife.bind(this);

        //Null
        rcvNulls.setV1OnClickListener(new Runnable() {
            @Override
            public void run() {
                ObservableV1Examples.invokeNull();
            }
        });
        rcvNulls.setV2OnClickListener(new Runnable() {
            @Override
            public void run() {
                ObservableV2Examples.invokeNull();
            }
        });

        //New types of 'Observable'
        rcvNewTypes.setV2OnClickListener(new Runnable() {
            @Override
            public void run() {
                ObservableV2Examples.invokeMaybe();
                ObservableV2Examples.invokeSingle();
                ObservableV2Examples.invokeCompletable();
            }
        });

        //New type of Subject - 'Processor'
        rcvNewProcessor.setV1OnClickListener(new Runnable() {
            @Override
            public void run() {
                ObservableV1Examples.invokeSubject();
            }
        });
        rcvNewProcessor.setV2OnClickListener(new Runnable() {
            @Override
            public void run() {
                ObservableV2Examples.invokeSubject();
                ObservableV2Examples.invokeProcessor();
            }
        });

        //Advanced example of RxJava vX in comparison
        rcvAdvancedExample.setV1OnClickListener(new Runnable() {
            @Override
            public void run() {
                ObservableV1Examples.invokeAdvanced();
            }
        });
        rcvAdvancedExample.setV2OnClickListener(new Runnable() {
            @Override
            public void run() {
                ObservableV2Examples.invokeAdvanced();
            }
        });
    }
}

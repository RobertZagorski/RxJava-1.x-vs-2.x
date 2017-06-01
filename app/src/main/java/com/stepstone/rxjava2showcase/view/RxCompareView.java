package com.stepstone.rxjava2showcase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stepstone.rxjava2showcase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 10/03/2017.
 */

public class RxCompareView extends ConstraintLayout {

    @BindView(R.id.title)
    TextView tvTitle;

    @BindView(R.id.buttonV1)
    Button buttonV1;

    @BindView(R.id.buttonV2)
    Button buttonV2;

    public RxCompareView(Context context) {
        super(context);
    }

    public RxCompareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.rx_compare_view, this);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RxCompareView);
        ButterKnife.bind(this);
        tvTitle.setText(attributes.getString(R.styleable.RxCompareView_rcv_title));
        attributes.recycle();
    }

    public RxCompareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setV1OnClickListener(final Runnable runnable) {
        buttonV1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                runnable.run();
            }
        });
    }

    public void setV2OnClickListener(final Runnable runnable) {
        buttonV2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                runnable.run();
            }
        });
    }
}

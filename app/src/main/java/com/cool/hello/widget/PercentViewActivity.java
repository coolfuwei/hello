package com.cool.hello.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cool.hello.R;
import com.org.sleepgod.widget.PercentView;

public class PercentViewActivity extends AppCompatActivity {

    private PercentView mPercentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent_view);
        mPercentView = (PercentView) findViewById(R.id.pv_view);
        mPercentView.setOnPrecentViewClickListener(new PercentView.OnPrecentViewClickListener() {
            @Override
            public void onClick(PercentView view) {
                mPercentView.setArriveProgress(80);
            }
        });
    }
}

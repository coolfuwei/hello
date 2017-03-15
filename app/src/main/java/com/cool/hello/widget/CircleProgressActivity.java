package com.cool.hello.widget;

import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cool.hello.R;
import com.org.sleepgod.widget.CircleProgressView;

public class CircleProgressActivity extends AppCompatActivity {

    private CircleProgressView mCircleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getClass().getSimpleName());

        mCircleProgressView = (CircleProgressView) findViewById(R.id.cpv_progress);
        mCircleProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            SystemClock.sleep(30);
                            final int finalI = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mCircleProgressView.setProgress(finalI);
                                }
                            });
                        }
                    }
                }.start();

            }
        });
    }
}

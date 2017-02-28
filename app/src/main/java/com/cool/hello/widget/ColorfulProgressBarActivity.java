package com.cool.hello.widget;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cool.hello.R;
import com.org.sleepgod.widget.ColorfulProgressBar;

public class ColorfulProgressBarActivity extends AppCompatActivity {

    private ColorfulProgressBar prefectProgressBar;
    private ColorfulProgressBar prefectProgressBar2;
    private ColorfulProgressBar prefectProgressBar3;
    private ColorfulProgressBar prefectProgressBar4;
    private ColorfulProgressBar prefectProgressBar5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorful_progress_bar);
        prefectProgressBar = (ColorfulProgressBar) findViewById(R.id.ppb);
        prefectProgressBar2 = (ColorfulProgressBar) findViewById(R.id.ppb2);
        prefectProgressBar3 = (ColorfulProgressBar) findViewById(R.id.ppb3);
        prefectProgressBar4 = (ColorfulProgressBar) findViewById(R.id.ppb4);
        prefectProgressBar5 = (ColorfulProgressBar) findViewById(R.id.ppb5);
        prefectProgressBar5.setMax(130);
        downLoad();
    }

    public void downLoad() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 20; i++) {
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            prefectProgressBar.setProgress(finalI);
                        }
                    });
                    SystemClock.sleep(40);
                }
                for (int i = 1; i <= 40; i++) {
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            prefectProgressBar2.setProgress(finalI);
                        }
                    });
                    SystemClock.sleep(40);
                }
                for (int i = 1; i <= 60; i++) {
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            prefectProgressBar3.setProgress(finalI);
                        }
                    });
                    SystemClock.sleep(40);
                }
                for (int i = 1; i <= 80; i++) {
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            prefectProgressBar4.setProgress(finalI);
                        }
                    });
                    SystemClock.sleep(40);
                }
                for (int i = 1; i <= 130; i++) {
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            prefectProgressBar5.setProgress(finalI);
                        }
                    });
                    SystemClock.sleep(40);
                }
            }
        }).start();
    }
}

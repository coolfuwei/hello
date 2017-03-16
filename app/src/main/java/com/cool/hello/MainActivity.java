package com.cool.hello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cool.hello.alertDialog.DialogActivity;
import com.cool.hello.common.LollipopActivity;
import com.cool.hello.effect.EffectActivity;
import com.cool.hello.greenDB.GreenDaoActivity;
import com.cool.hello.scan.ScanActivity;
import com.cool.hello.sign.SignActivity;
import com.cool.hello.surfaceview.CameraActivity;
import com.cool.hello.timePick.TimePickActivity;
import com.cool.hello.widget.WidgetActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void click1(View v) {
        startActivity(new Intent(this, WidgetActivity.class));
    }

    public void click2(View v) {
        startActivity(new Intent(this, LollipopActivity.class));
    }

    public void click3(View v) {
        startActivity(new Intent(this, CameraActivity.class));
    }

    public void click4(View v) {
        startActivity(new Intent(this, TimePickActivity.class));
    }

    public void click5(View v) {
        startActivity(new Intent(this, DialogActivity.class));
    }

    public void click6(View v) {
        startActivity(new Intent(this, ScanActivity.class));
    }

    public void click7(View v) {
        startActivity(new Intent(this, SignActivity.class));
    }

    public void click8(View v) {
        startActivity(new Intent(this, GreenDaoActivity.class));
    }
    public void click9(View v) {
        startActivity(new Intent(this, EffectActivity.class));
    }
}

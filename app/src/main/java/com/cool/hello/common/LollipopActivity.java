package com.cool.hello.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cool.hello.R;

public class LollipopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lollipop);
    }

    public void click1(View v){
        startActivity(new Intent(this,FirstActivity.class));
    }
    public void click2(View v){
        startActivity(new Intent(this,CoordinatorLayoutActivity.class));
    }
}

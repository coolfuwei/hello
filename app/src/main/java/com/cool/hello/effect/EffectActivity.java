package com.cool.hello.effect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cool.hello.R;

public class EffectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effect);
        getSupportActionBar().setTitle(getClass().getSimpleName());
    }


    public void click1(View view){
        startActivity(new Intent(this,BezierCurveActivity.class));
    }
    public void click2(View view){
        startActivity(new Intent(this,TimeLineActivity.class));
    }
}

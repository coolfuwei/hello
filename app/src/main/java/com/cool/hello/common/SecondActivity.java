package com.cool.hello.common;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.cool.hello.R;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mImageView = (ImageView) findViewById(R.id.iv_img);
        mImageView.setOnClickListener(this);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("SecondActivity");
        }

    }


    @Override
    public void onClick(View v) {
        supportFinishAfterTransition();
    }
}

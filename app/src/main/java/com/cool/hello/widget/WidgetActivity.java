package com.cool.hello.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cool.hello.R;

public class WidgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
    }

    public void click1(View v){
        startActivity(new Intent(this,ColorfulProgressBarActivity.class));
    }
    public void click2(View v){
        startActivity(new Intent(this,FlowLayoutActivity.class));
    }
    public void click3(View v){
        startActivity(new Intent(this,WatchBoardViewActivity.class));
    }
    public void click4(View v){
        startActivity(new Intent(this,StickerViewActivity.class));
    }
    public void click5(View v){
        startActivity(new Intent(this,PullViewActivity.class));
    }
    public void click6(View v){
        startActivity(new Intent(this,ColorfulHeadViewActivity.class));
    }
    public void click7(View v){
        startActivity(new Intent(this,CustomNumAnimViewActivity.class));
    }
    public void click8(View v){
        startActivity(new Intent(this,PercentViewActivity.class));
    }
    public void click9(View v){
        startActivity(new Intent(this,LineChartViewActivity.class));
    }
}

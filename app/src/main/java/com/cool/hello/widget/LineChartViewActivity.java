package com.cool.hello.widget;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.cool.hello.R;
import com.org.sleepgod.widget.linechart.ChartPoint;
import com.org.sleepgod.widget.linechart.LineChartView;

import java.util.ArrayList;
import java.util.List;

public class LineChartViewActivity extends AppCompatActivity {

    private LineChartView mLineChartView;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_view);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(getClass().getSimpleName());
        mLineChartView = (LineChartView) findViewById(R.id.lcv_view);
        mResultTextView = (TextView) findViewById(R.id.tv_result);
        mLineChartView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initData();
                mLineChartView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });



        mLineChartView.setOnPointClickListener(new LineChartView.OnPointClickListener() {
            @Override
            public void onPointClick(int position, ChartPoint point) {
                mResultTextView.setText("(" + point.getxData() + "," + point.getyData() + ")" );
            }
        });

    }

    private void initData() {
        List<Integer> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();
        for (int i = 0 ; i< 7 ; i++){
            xList.add(i + 1);
            int y = (int) (Math.random()*70 + 1);
            yList.add(y);
        }

        mLineChartView.setDataList(xList,yList);
    }


    public void setData(View view){
        initData();
    }
}

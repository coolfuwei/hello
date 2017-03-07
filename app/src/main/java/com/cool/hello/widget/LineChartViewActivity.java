package com.cool.hello.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cool.hello.R;
import com.org.sleepgod.widget.linechart.LineChartView;

import java.util.ArrayList;
import java.util.List;

public class LineChartViewActivity extends AppCompatActivity {

    private LineChartView mLineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_view);
        mLineChartView = (LineChartView) findViewById(R.id.lcv_view);
        initData();
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
}

package com.cool.hello.timePick;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cool.hello.R;
import com.org.sleepgod.datepick.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimePickActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mEnterTimeTextView;
    private TextView mEnterTimeTextView2;
    private CustomDatePicker customDatePicker;
    private CustomDatePicker customDatePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_pick);
        mEnterTimeTextView = (TextView) findViewById(R.id.tv_enter_time);
        mEnterTimeTextView2 = (TextView) findViewById(R.id.tv_enter_time2);
        mEnterTimeTextView.setOnClickListener(this);
        mEnterTimeTextView2.setOnClickListener(this);
        initTimePicker();
    }

    private void initTimePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        mEnterTimeTextView.setText(now.split(" ")[0]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 5);
        Date nextYearDate = calendar.getTime();

        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 10);
        Date preYearDate = calendar.getTime();

        String nextYear = sdf.format(nextYearDate);
        String preYear = sdf.format(preYearDate);

        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time, String tag) {
                if ("enterTime".equals(tag)) {
                    mEnterTimeTextView.setText(time.split(" ")[0]);
                } else if ("leaveTime".equals(tag)) {
//                    mLeaveTimeTextView.setText(time.split(" ")[0]);
                }
            }
        }, preYear, nextYear);
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time, String tag) {
                mEnterTimeTextView2.setText(time);
            }
        },preYear,nextYear);
        customDatePicker2.showSpecificTime(true); // 不显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_enter_time:
                customDatePicker.show(mEnterTimeTextView.getText().toString(),"enterTime");
                break;
            case R.id.tv_enter_time2:
                customDatePicker2.show(mEnterTimeTextView.getText().toString());
                break;
        }

    }
}

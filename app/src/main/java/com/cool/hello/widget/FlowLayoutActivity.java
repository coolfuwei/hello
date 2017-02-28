package com.cool.hello.widget;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.cool.hello.R;
import com.org.sleepgod.utils.UIUtils;
import com.org.sleepgod.widget.SearchFlowLayout;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class FlowLayoutActivity extends Activity {

    private SearchFlowLayout mSearchFlowLayout;
    private String[] mNames = new String[]{"大主宰","龙王传说","一念永恒","雪鹰领主","帝霸","最强狂兵","美食供应商","我是大明星","全职法师","我的贴身校花","重生完美时代"
    ,"318女生宿舍"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);
        mSearchFlowLayout = (SearchFlowLayout) findViewById(R.id.fl_view);
        for (int i = 0; i < mNames.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(mNames[i]);
            textView.setBackgroundResource(R.drawable.text_bg);
            GradientDrawable gradientDrawable = (GradientDrawable) textView.getBackground();
            gradientDrawable.setStroke(1, UIUtils.randomColor());
            mSearchFlowLayout.addView(textView);
        }

        mSearchFlowLayout.setOnItemClickListener(new SearchFlowLayout.OnItemClickListener() {
            @Override
            public void click(int position) {
                Toast.makeText(FlowLayoutActivity.this,mNames[position],Toast.LENGTH_SHORT).show();
            }
        });
    }
}

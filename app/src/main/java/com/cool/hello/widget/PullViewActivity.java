package com.cool.hello.widget;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cool.hello.R;

import java.lang.reflect.Field;

public class PullViewActivity extends Activity implements View.OnClickListener {

    private Button mBackButton;
    private PullLinearLayout pullLinearLayout;
    private ImageView mBackgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_view);
        setImmerse();
        mBackButton = (Button) findViewById(R.id.bt_back);
        pullLinearLayout = (PullLinearLayout) findViewById(R.id.ll_root);
        mBackgroundImageView = (ImageView) findViewById(R.id.iv_background);
        mBackButton.setOnClickListener(this);

        mBackgroundImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = mBackgroundImageView.getHeight();
                int intrinsicHeight = mBackgroundImageView.getDrawable().getIntrinsicHeight();
                pullLinearLayout.setViewHight(mBackgroundImageView,height,intrinsicHeight);
                mBackgroundImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
    /**
     * 设置沉浸式状态栏
     */
    private void setImmerse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            else {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            RelativeLayout mHeadRelativeLayout = (RelativeLayout) findViewById(R.id.rl_container);
            int statusBarHeight = getStatusBarHeight();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mHeadRelativeLayout.getLayoutParams();
            params.topMargin = statusBarHeight;
            mHeadRelativeLayout.setLayoutParams(params);
        }
    }
    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_back){
            finish();
        }
    }
}

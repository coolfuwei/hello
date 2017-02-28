package com.cool.hello.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by cool on 2016/12/2.
 */

public class PullLinearLayout extends LinearLayout {
    private ImageView mBackgroundImageView;
    private int mHeight;
    private int mIntrinsicHeight;

    public PullLinearLayout(Context context) {
        super(context);
    }

    public PullLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int downX = 0;
    int downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public void setViewHight(ImageView backgroundImageView, int height, int intrinsicHeight) {
        mBackgroundImageView = backgroundImageView;
        mHeight = height;
        mIntrinsicHeight = intrinsicHeight;
        Log.e("399", "mHeight: " + mHeight + " mIntrinsicHeight: " + mIntrinsicHeight);
    }
}

package com.org.sleepgod.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 *  在白板上实现签名功能
 * Created by cool on 2017/2/7.
 */

public class SignTouchView extends View {

    private int mWidth;//屏幕的宽
    private int mHight;//屏幕的高
    private Paint mPaint;
    private Path mPath;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    public SignTouchView(Context context) {
        this(context, null);
    }

    public SignTouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 去除锯齿
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);

        mPath = new Path();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap,0,0,null);
        canvas.drawPath(mPath,mPaint);
    }

    float startX;
    float startY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                mPath.moveTo(startX,startY);
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                float currentY = event.getY();

                mPath.lineTo(currentX,currentY);
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath,mPaint);
                break;
        }

        invalidate();

        return true;
    }

    public Bitmap getBitmap(){
        return mBitmap;
    }

    public void clear(){
        if(mCanvas != null){
            mPath.reset();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHight = h;

        mBitmap = Bitmap.createBitmap(mWidth, mHight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.WHITE);
    }
}

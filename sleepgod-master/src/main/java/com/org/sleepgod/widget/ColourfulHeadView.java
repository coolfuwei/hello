package com.org.sleepgod.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.org.sleepgod.R;
import com.org.sleepgod.utils.UIUtils;

/**
 * Created by cool on 2017/2/27.
 */

public class ColourfulHeadView extends View {

    private int mRadius;
    private String mName;
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private int mWidth;
    private int mHight;

    public ColourfulHeadView(Context context) {
        this(context, null);
    }

    public ColourfulHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColourfulHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColourfulHeadView);
        try {
            mRadius = (int) ta.getDimension(R.styleable.ColourfulHeadView_radius, UIUtils.dp2px(context, 40));
            mName = ta.getString(R.styleable.ColourfulHeadView_name);
        } catch (Exception e) {
            mRadius = UIUtils.dp2px(context, 40);
            mName = "";
        } finally {
            ta.recycle();
        }

        init();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(UIUtils.randomColor());
        mCirclePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        Rect rect = new Rect();
        mTextPaint.getTextBounds(mName, 0, mName.length(), rect);
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();

        float x = mWidth / 2 - rect.width() / 2;
        float y = mHight / 2 - (fontMetricsInt.ascent + fontMetricsInt.descent) / 2;
        canvas.drawText(mName, x, y, mTextPaint);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mWidth / 2, mHight / 2, mRadius, mCirclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widtheMode = MeasureSpec.getMode(widthMeasureSpec);
        int hightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widtheMode != MeasureSpec.EXACTLY || hightMode != MeasureSpec.EXACTLY) {
            int w = MeasureSpec.makeMeasureSpec(mRadius * 2, MeasureSpec.EXACTLY);
            super.onMeasure(w, w);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHight = h;
    }


    public void setName(String text) {
        this.mName = text;
    }
}

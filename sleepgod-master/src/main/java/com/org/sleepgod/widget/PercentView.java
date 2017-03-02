package com.org.sleepgod.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.org.sleepgod.R;
import com.org.sleepgod.utils.UIUtils;

/**
 * 百分比控件
 * Created by cool on 2017/3/2.
 */

public class PercentView extends View {

    private int mRadius;//半径
    private int mCircleColor;//圆的颜色
    private int mRingWidth;//圆环宽度
    private int mRingColor;//圆环颜色
    private int mTextSize;//文本字体大小
    private int mTextColor;//文子颜色
    private Paint mCirclePaint;
    private Paint mRingPaint;
    private Paint mTextPaint;
    private float mCurrentProgress = 0;

    private OnPrecentViewClickListener mListener;

    public void setOnPrecentViewClickListener(OnPrecentViewClickListener listener) {
        this.mListener = listener;
    }

    public interface OnPrecentViewClickListener {
        void onClick(PercentView view);
    }

    public PercentView(Context context) {
        this(context, null);
    }

    public PercentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PercentView);
        mRadius = (int) ta.getDimension(R.styleable.PercentView_percentRadius, UIUtils.dp2px(context, 60));
        mCircleColor = ta.getColor(R.styleable.PercentView_percentCircleColor, context.getResources().getColor(R.color.purple));
        mRingWidth = (int) ta.getDimension(R.styleable.PercentView_percentRingWidth, UIUtils.dp2px(context, 10));
        mRingColor = ta.getColor(R.styleable.PercentView_percentRingColor, context.getResources().getColor(R.color.dark_blue));
        mTextSize = (int) ta.getDimension(R.styleable.PercentView_percentTextSize, UIUtils.sp2px(context, 20));
        mTextColor = ta.getColor(R.styleable.PercentView_percentTextColor, context.getResources().getColor(R.color.green));
        ta.recycle();

        init();
    }

    private void init() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mRingPaint.setStrokeWidth(mRingWidth);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(PercentView.this);
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
        drawRing(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        String text = mCurrentProgress + "%";
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float x = getWidth() / 2 - rect.width() / 2;
        float y = getHeight() / 2 - (fontMetrics.ascent + fontMetrics.descent)/2;
        canvas.drawText(text, x, y, mTextPaint);
    }

    private void drawRing(Canvas canvas) {
        RectF rectF = new RectF();
        rectF.left = getWidth() / 2 - mRadius + mRingWidth / 2;
        rectF.top = getHeight() / 2 - mRadius + mRingWidth / 2;
        rectF.right = getWidth() - mRingWidth / 2;
        rectF.bottom = getHeight() - mRingWidth / 2;
        canvas.drawArc(rectF, -90, 360 * (mCurrentProgress / 100), false, mRingPaint);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mCirclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
    }

    private int measureSize(int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = mRadius * 2;
        }
        return result;
    }

    public void setArriveProgress(float arriveProgress) {
        mCurrentProgress = 0;
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(mCurrentProgress, arriveProgress);
        valueAnimator.setDuration((long) Math.abs(arriveProgress - mCurrentProgress) * 20);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mCurrentProgress = Math.round(value * 10) / 10;
                invalidate();
            }
        });
        valueAnimator.start();
    }
}

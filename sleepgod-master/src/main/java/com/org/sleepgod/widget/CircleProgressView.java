package com.org.sleepgod.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.org.sleepgod.R;
import com.org.sleepgod.utils.UIUtils;

/**
 * Created by cool on 2017/3/14.
 */

public class CircleProgressView extends View {

    private int mRadius;//圆半径
    private int mArcWidth;//弧线宽度
    private int mScaleCount;//刻度个数
    private int mStartColor;//渐变起始颜色
    private int mEndColor;//渐变终止颜色
    private int[] mSectionArry;
    private String mLabelText;//标签说明文本
    private int mTextColor;//标签说明文本颜色
    private int mProgressTextSize;//百分比文本字体大小
    private int mLabelTextSize;//标签说明字体大小

    private Paint mArcBackgroundPaint;//圆弧背景画笔
    private Paint mArcFrontPaint;
    private Paint mLinePaint;
    private Paint mLabelPaint;
    private Paint mProgressTextPaint;
    private int mWidth;
    private int mHigh;
    private RectF mArcRectF;
    private Paint mCirclePaint;
    private int mProgress = 0;

    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        mRadius = (int) ta.getDimension(R.styleable.CircleProgressView_CricleRadius, UIUtils.dp2px(context,100));
        mArcWidth = (int) ta.getDimension(R.styleable.CircleProgressView_CircleArcWidth,UIUtils.dp2px(context,10));
        mScaleCount = ta.getInt(R.styleable.CircleProgressView_CircleScaleCount,24);
        mStartColor = ta.getColor(R.styleable.CircleProgressView_CircleStartColor, Color.parseColor("#DC143C"));
        mEndColor = ta.getColor(R.styleable.CircleProgressView_CircleEndColor,Color.parseColor("#EE7AE9"));
        mSectionArry = new int[]{mStartColor,mEndColor};
        mLabelText = ta.getString(R.styleable.CircleProgressView_CircleLabelText);
        mTextColor = ta.getColor(R.styleable.CircleProgressView_CircleTextColor,Color.parseColor("#4F5F6F"));
        mProgressTextSize = (int) ta.getDimension(R.styleable.CircleProgressView_CircleProgressTextSize,mRadius/2);
        mLabelTextSize = (int) ta.getDimension(R.styleable.CircleProgressView_CircleLabelTextSize,UIUtils.sp2px(context,16));

        ta.recycle();

        init();
    }

    private void init() {
        mArcRectF = new RectF();

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.parseColor("#EBEBEB"));
        mCirclePaint.setStyle(Paint.Style.FILL);

        mArcBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcBackgroundPaint.setStyle(Paint.Style.STROKE);
        mArcBackgroundPaint.setColor(Color.LTGRAY);
        mArcBackgroundPaint.setStrokeWidth(mArcWidth);

        mArcFrontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcFrontPaint.setStyle(Paint.Style.STROKE);
        mArcFrontPaint.setStrokeWidth(mArcWidth);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStrokeWidth(UIUtils.dp2px(getContext(),2));

        mLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelPaint.setColor(mTextColor);
        mLabelPaint.setTextSize(mLabelTextSize);

        mProgressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressTextPaint.setColor(mTextColor);
        mProgressTextPaint.setTextSize(mProgressTextSize);

        if(TextUtils.isEmpty(mLabelText)){
            mLabelText = "";
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawArcBackground(canvas);//画圆弧背景
        drawArc(canvas);//画渐变圆弧
        drawText(canvas);
        drawDivideLine(canvas);//画白色分割线
    }

    /**
     * 画白色分割线
     * @param canvas
     */
    private void drawDivideLine(Canvas canvas) {
        canvas.save();
        for (int i = 0 ; i<mScaleCount ; i++) {
            canvas.rotate(360 / mScaleCount, mWidth / 2, mHigh / 2);
            canvas.drawLine(mWidth / 2, 0, mWidth / 2, mArcWidth, mLinePaint);
        }
        canvas.restore();
    }

    /**
     * 画文本
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        //画进度文本
        String progress = mProgress + "%";
        Paint.FontMetricsInt fontMetricsInt = mProgressTextPaint.getFontMetricsInt();
        Rect rect = new Rect();
        mProgressTextPaint.getTextBounds(progress,0,progress.length(),rect);
        canvas.drawText(progress,mWidth/2 - rect.width()/2,mHigh/2 - (fontMetricsInt.ascent + fontMetricsInt.descent)/2,mProgressTextPaint);

        //画标签文本
        mLabelPaint.getTextBounds(mLabelText,0,mLabelText.length(),rect);
        canvas.drawText(mLabelText,mWidth/2 - rect.width()/2,mHigh/3,mLabelPaint);
    }

    /**
     * 画渐变圆弧
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        int angle = 360*mProgress/100;
        //线性渐变
        LinearGradient linearGradient = new LinearGradient(mWidth/2,0,mWidth/2,mHigh,mSectionArry,null, Shader.TileMode.CLAMP);
        mArcFrontPaint.setShader(linearGradient);

        //圆形渐变
//        SweepGradient sweepGradient = new SweepGradient(mWidth/2,mHigh/2,mSectionArry,null);
//        mArcFrontPaint.setShader(sweepGradient);
//        canvas.save();
//        canvas.rotate(-90,mWidth/2,mHigh/2);
        canvas.drawArc(mArcRectF,-90,angle,false,mArcFrontPaint);
//        canvas.restore();
    }

    /**
     * 画圆弧背景
     * @param canvas
     */
    private void drawArcBackground(Canvas canvas) {
        canvas.drawCircle(mWidth/2,mHigh/2,mRadius,mCirclePaint);

        mArcRectF.set(mArcWidth/2,mArcWidth/2,mWidth - mArcWidth/2,mHigh-mArcWidth/2);
        canvas.drawArc(mArcRectF,-90,360,false,mArcBackgroundPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSpec(widthMeasureSpec),measureSpec(heightMeasureSpec));
    }

    private int measureSpec(int measureSpec){
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result;

        if(mode == MeasureSpec.EXACTLY){
            result = size;
        }else {
            result = mRadius*2;
        }

        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHigh = h;
    }


    public void setProgress(int progress){
        this.mProgress = progress;
        invalidate();
    }
}

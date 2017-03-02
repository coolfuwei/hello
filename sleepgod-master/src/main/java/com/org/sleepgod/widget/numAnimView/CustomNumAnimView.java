package com.org.sleepgod.widget.numAnimView;

import android.animation.Animator;
import android.animation.ValueAnimator;
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

import java.util.Random;

/**
 * Created by cool on 2017/2/28.
 */

public class CustomNumAnimView extends View {
    private int mRoundColor;    //圆的颜色
    private int mTextColor;    //数字的颜色
    private float mTextSize;    //数字字体大小
    private float mRoundRadius;    //圆的半径
    private Paint mPaint;     //画笔
    private Rect mTextRect;    //包裹数字的矩形
    private String middleNum = "1";
    private String leftNum = "1";
    private String rightNum = "1";

    private boolean isFirstInit = false;   //是否是第一次初始化
    private CustomPoint mMiddlePoint;   //中间的数字的实时点
    private ValueAnimator mMiddleAnim;   //中间数字动画
    private boolean isMiddleNumInvalidate = false;    //中间数字是否重绘界面
    private Random mRandom;

    public CustomNumAnimView(Context context) {
        this(context, null);
    }

    public CustomNumAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomNumAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomNumAnimView);
        mRoundColor = ta.getColor(R.styleable.CustomNumAnimView_round_color, Color.BLUE);
        mTextColor = ta.getColor(R.styleable.CustomNumAnimView_text_color, Color.WHITE);
        mTextSize = ta.getDimension(R.styleable.CustomNumAnimView_text_size, UIUtils.sp2px(context, 16.0f));
        mRoundRadius = ta.getDimension(R.styleable.CustomNumAnimView_round_radius, UIUtils.sp2px(context, 50.0f));
        ta.recycle();
        init();
    }

    private void init() {

        mRandom = new Random();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
        mTextRect = new Rect();    //得到数字矩形的宽高，以用来画数字的时候纠正数字的位置
        mPaint.getTextBounds(middleNum, 0, middleNum.length(), mTextRect);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);

        if(!isFirstInit){
            mMiddlePoint = new CustomPoint(getMeasuredWidth()/2 - mTextRect.width()/2,getMeasuredHeight()/2 - mRoundRadius);
            drawText(canvas);
            startAnimation();
            isFirstInit = true;
        } else {
            drawText(canvas);
        }
    }

    /**
     * 开始动画
     */
    private void startAnimation() {
        CustomPoint startPoint = new CustomPoint(getMeasuredWidth()/2 - mTextRect.width()/2,getMeasuredHeight()/2 - mRoundRadius);
        CustomPoint endPoint = new CustomPoint(getMeasuredWidth()/2 - mTextRect.width()/2,getMeasuredHeight()/2 + mRoundRadius);
        mMiddleAnim = ValueAnimator.ofObject(new CustomPointEvaluator(), startPoint, endPoint);

        mMiddleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMiddlePoint = (CustomPoint) animation.getAnimatedValue();
                isMiddleNumInvalidate = true;
                invalidate();
            }
        });
        mMiddleAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                middleNum = mRandom.nextInt(9) + "";
                leftNum = mRandom.nextInt(9) + "";
                rightNum = mRandom.nextInt(9) + "";
            }
        });
        mMiddleAnim.setDuration(300);
        mMiddleAnim.setRepeatCount(ValueAnimator.INFINITE);
    }

    private void drawText(Canvas canvas) {
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        if(isMiddleNumInvalidate) {
            canvas.drawText(middleNum, mMiddlePoint.getX(), mMiddlePoint.getY(), mPaint);
            canvas.drawText(leftNum, mMiddlePoint.getX() - mRoundRadius/2, mMiddlePoint.getY() + UIUtils.dp2px(getContext(),55), mPaint);
            canvas.drawText(rightNum, mMiddlePoint.getX() + mRoundRadius/2, mMiddlePoint.getY() + UIUtils.dp2px(getContext(),45), mPaint);
            isMiddleNumInvalidate = false;
        }

    }

    /**
     * 画圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        float centerX = getMeasuredWidth() / 2;
        float centerY = getMeasuredHeight() / 2;
        mPaint.setColor(mRoundColor);
        canvas.drawCircle(centerX, centerY, mRoundRadius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int hightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY || hightMode != MeasureSpec.EXACTLY) {
            int width = MeasureSpec.makeMeasureSpec((int) (mRoundRadius * 2), MeasureSpec.EXACTLY);
            super.onMeasure(width, width);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 开始动画
     */
    public void startAnim(){
        mMiddleAnim.start();
    }

    public void stopAnim(){
        mMiddleAnim.cancel();
    }
}

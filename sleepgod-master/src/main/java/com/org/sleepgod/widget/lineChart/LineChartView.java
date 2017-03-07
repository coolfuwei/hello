package com.org.sleepgod.widget.linechart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.org.sleepgod.R;
import com.org.sleepgod.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cool on 2017/3/3.
 */

public class LineChartView extends View {

    private int mLineColor;//线的颜色
    private int mTextColor;//坐标字体颜色
    private int mPointColor;//圆点颜色
    private Path mLinePath;
    private Paint mLinePaint;
    private Paint mTextPaint;
    private Paint mPointPaint;

    private List<ChartPoint> mChartPoints = new ArrayList<>();
    private List<ChartPoint> mLastChartPoints;
    private int mOffset;//预留文字空间
    private int xMax = 7;//x轴上的最大值
    private int yMax = 70;//y轴上最大值
    private int mScaleLineWidth;//刻度线的宽
    private int mScaleXWidth;//x轴每份的宽度
    private int mScaleYWidth;//y轴每份的宽度
    private int mPointRadius;//小圆点的半径
    private int mWidth;
    private int mHight;

    public LineChartView(Context context) {
        this(context,null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        mLineColor = ta.getColor(R.styleable.LineChartView_lineColor,getResources().getColor(R.color.dark_yellow));
        mTextColor = ta.getColor(R.styleable.LineChartView_LineChartTextColor,getResources().getColor(R.color.text_color_normal));
        mPointColor = ta.getColor(R.styleable.LineChartView_LinePointColor,getResources().getColor(R.color.yellow));
        ta.recycle();

        init();
    }

    private void init() {
        mOffset = UIUtils.dp2px(getContext(),20);
        mScaleLineWidth = UIUtils.dp2px(getContext(),5);
        mPointRadius = UIUtils.dp2px(getContext(),4);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(UIUtils.dp2px(getContext(),2));
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);

        mLinePath = new Path();

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(UIUtils.sp2px(getContext(),12));

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(mPointColor);
        mPointPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCoordinates(canvas);//画坐标轴
        drawScale(canvas);//画刻度
        drawBrokenLine(canvas);//画折线

    }

    /**
     * 画折线
     * @param canvas
     */
    private void drawBrokenLine(Canvas canvas) {
        mLinePath.reset();
        for (int i = 0; i < mChartPoints.size(); i++) {
            ChartPoint chartPoint = mChartPoints.get(i);
            if(i == 0)
            mLinePath.moveTo(chartPoint.getX(),chartPoint.getY());
            mLinePath.lineTo(chartPoint.getX(),chartPoint.getY());

            canvas.drawCircle(chartPoint.getX(),chartPoint.getY(),mPointRadius,mPointPaint);
        }
        canvas.drawPath(mLinePath,mLinePaint);
    }

    /**
     * 画坐标轴
     */
    private void drawCoordinates(Canvas canvas) {
        mLinePath.reset();
        mLinePath.moveTo(2*mOffset/3,mOffset + 2*mOffset/3);
        mLinePath.lineTo(mOffset,mOffset);
        mLinePath.lineTo(mOffset + mOffset/3,mOffset + 2*mOffset/3);
        mLinePath.lineTo(mOffset,mOffset);
        mLinePath.lineTo(mOffset,mHight- mOffset);
        mLinePath.lineTo(mWidth - mOffset,mHight - mOffset);
        mLinePath.lineTo(mWidth - mOffset - 2*mOffset/3,mHight - mOffset - mOffset/3);
        mLinePath.lineTo(mWidth - mOffset,mHight - mOffset);
        mLinePath.lineTo(mWidth - mOffset - 2*mOffset/3,mHight - mOffset + mOffset/3);
        canvas.drawPath(mLinePath,mLinePaint);
    }

    /**
     * 画刻度
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        for (int i = 0; i < mChartPoints.size(); i++) {
            //画x轴刻度
            int x1 = mOffset + (i + 1)*mScaleXWidth;
            int startY1 = mHight - mOffset;
            int endY1 = mHight - mOffset - mScaleLineWidth;
            canvas.drawLine(x1,startY1,x1,endY1,mLinePaint);
           //画x轴的刻度值
            String xValue = i + 1 + "";
            Rect rect = new Rect();
            mTextPaint.getTextBounds(xValue,0,xValue.length(),rect);
            canvas.drawText(xValue,x1 - rect.width()/2,mHight - rect.height()/2,mTextPaint);

            int startX2 = mOffset;
            int y2 = mHight - (i + 1)*mScaleXWidth - mOffset;
            int endX2 = mOffset+mScaleLineWidth;
            //画y轴刻度
            canvas.drawLine(startX2,y2,endX2,y2,mLinePaint);
            String yValue = (i + 1)*10 + "";
            mTextPaint.getTextBounds(yValue,0,yValue.length(),rect);
            canvas.drawText(yValue, 0,y2 + rect.height()/2,mTextPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measuredDimension(widthMeasureSpec),measuredDimension(heightMeasureSpec));
    }

    private int measuredDimension(int measureSpec){
        int result;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);
        if(mode == MeasureSpec.EXACTLY){
            result = size;
        }else {
           result = MeasureSpec.makeMeasureSpec(UIUtils.dp2px(getContext(),300),MeasureSpec.EXACTLY);
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                for (int i = 0; i < mChartPoints.size(); i++) {
                    ChartPoint chartPoint = mChartPoints.get(i);
                    double dx = Math.pow(x - chartPoint.getX(), 2);
                    double dy = Math.pow(y - chartPoint.getY(), 2);
                    if(Math.sqrt(dx + dy) <= mPointRadius + 5){
                        if(listener != null){
                            listener.onPointClick(i,chartPoint);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
            break;
            case MotionEvent.ACTION_UP:
            break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置数据
     * @param xList
     * @param yList
     */
    public void setDataList(List<Integer> xList,List<Integer> yList){
        if(xList == null || yList ==null || xList.size() ==0 || yList.size() ==0){
            throw new IllegalArgumentException("没有数据");
        }

        if(xList.size() != yList.size()){
            throw new IllegalArgumentException("x,y轴数据长度不一致");
        }

        setPointData(xList,yList);
        setPointAnim();
    }

    /**
     * 设置x,y的数值和计算x,y在坐标轴上点的坐标
     * @param xList
     * @param yList
     */
    private void setPointData(List<Integer> xList, List<Integer> yList) {
        mChartPoints.clear();
        mScaleXWidth = (mWidth - 3*mOffset)/xMax;
        mScaleYWidth = (mHight - 3*mOffset)/yMax;
        for (int i = 0; i < xList.size(); i++) {
            ChartPoint chartPoint = new ChartPoint();
            //设置坐标点的xy数据
            chartPoint.setxData(xList.get(i));
            chartPoint.setyData(yList.get(i));

            //计算x,y坐标值
            chartPoint.setX(mOffset + xList.get(i)*mScaleXWidth);
            chartPoint.setY(mHight - mOffset -yList.get(i)*mScaleYWidth);

            mChartPoints.add(chartPoint);
        }
    }


    private void setPointAnim() {
        for (int i = 0; i < mChartPoints.size(); i++) {
            final ChartPoint chartPoint = mChartPoints.get(i);
            ValueAnimator valueAnimator;
            if(mLastChartPoints != null && mLastChartPoints.size() >0){
                ChartPoint lastChartPoint = mLastChartPoints.get(i);
                valueAnimator = ValueAnimator.ofInt(lastChartPoint.getY(),chartPoint.getY());
            }else {
                valueAnimator = ValueAnimator.ofInt(mHight - mOffset,chartPoint.getY());
            }
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int y = (int) animation.getAnimatedValue();
                    chartPoint.setY(y);
                    invalidate();
                }
            });
            valueAnimator.start();

        }
        mLastChartPoints = mChartPoints;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHight = h;
    }


    private OnPointClickListener listener;

    public void setOnPointClickListener(OnPointClickListener listener){
        this.listener = listener;
    }

    public interface OnPointClickListener{
        void onPointClick(int position,ChartPoint point);
    }
}

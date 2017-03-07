package com.org.sleepgod.widget.linechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
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
    private int mOffset;//预留文字空间
    private int xMax = 7;//x轴上的最大值
    private int yMax = 70;//y轴上最大值

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

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(UIUtils.dp2px(getContext(),2));
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);

        mLinePath = new Path();

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(UIUtils.sp2px(getContext(),16));

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(mPointColor);
        mPointPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {

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
           result = MeasureSpec.makeMeasureSpec(UIUtils.dp2px(getContext(),350),MeasureSpec.EXACTLY);
        }
        return result;
    }

    public void setDataList(List<Integer> xList,List<Integer> yList){
        if(xList == null || yList ==null || xList.size() ==0 || yList.size() ==0){
            throw new IllegalArgumentException("没有数据");
        }

        if(xList.size() != yList.size()){
            throw new IllegalArgumentException("x,y轴数据长度不一致");
        }

        setPointData(xList,yList);
    }

    private void setPointData(List<Integer> xList, List<Integer> yList) {
        mChartPoints.clear();
        for (int i = 0; i < xList.size(); i++) {
            ChartPoint chartPoint = new ChartPoint();
            //设置坐标点的xy数据
            chartPoint.setxData(xList.get(i));
            chartPoint.setyData(yList.get(i));

            //计算x,y坐标值
            chartPoint.setX(mOffset + xList.get(i)*(getWidth()));

        }
    }
}

package com.cool.hello.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cool.hello.R;
import com.org.sleepgod.utils.BezierUtil;
import com.org.sleepgod.utils.UIUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BezierCurveActivity extends AppCompatActivity {

    private RelativeLayout mRootRelativeLayout;
    private RecyclerView mRecyclerView;
    private ImageView mShoppingCartImageView;
    private TextView mCountTextView;

    private List<Integer> mImgIds = new ArrayList<>();
    private int[] mShoppingCartPositions = new int[2];
    int count =0;
    private ActionBar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_curve);
        supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(getClass().getSimpleName());


        initData();
        initView();
    }

    private void initData() {
        mImgIds.add(R.drawable.coin);
        mImgIds.add(R.drawable.coin1);
        mImgIds.add(R.drawable.coin91);
    }

    private void initView() {
        mRootRelativeLayout = (RelativeLayout) findViewById(R.id.rl_root);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_bezier);
        mShoppingCartImageView = (ImageView) findViewById(R.id.iv_shopping_cart);
        mCountTextView = (TextView) findViewById(R.id.cart_count);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new BezierApapter());

        mShoppingCartImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mShoppingCartImageView.getLocationInWindow(mShoppingCartPositions);
                mShoppingCartImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public class BezierApapter extends RecyclerView.Adapter<BezierViewHolder>{

        @Override
        public BezierViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(BezierCurveActivity.this).inflate(R.layout.item_bezier,parent,false);
            return new BezierViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final BezierViewHolder holder, final int position) {
            holder.mImage.setImageResource(mImgIds.get(position));
            holder.mBuyTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int[] location = new int[2];
                    holder.mImage.getLocationInWindow(location);
                    addImgToRelativeLayout(holder.mImage,location);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mImgIds.size();
        }
    }

    /**
     * 加入动画
     * @param iv
     * @param location
     */
    private void addImgToRelativeLayout(ImageView iv, int[] location) {
        int x = location[0];
        int y = location[1];
        int height = supportActionBar.getHeight();
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)UIUtils.sp2px(this,20),(int)UIUtils.sp2px(this,20));
        imageView.setImageDrawable(iv.getDrawable());
        imageView.setLayoutParams(params);
        mRootRelativeLayout.addView(imageView);

        int startX = x + iv.getWidth()/2;
        int startY = y-height-getStatusBarHeight() + iv.getHeight()/3;
        imageView.setTranslationX(startX);
        imageView.setTranslationY(startY);

        int endX = mShoppingCartPositions[0] + mShoppingCartImageView.getWidth()/2;
        int endY = mShoppingCartPositions[1] -height-getStatusBarHeight() + mShoppingCartImageView.getHeight()/3;

        PointF startPoint = new PointF(startX,startY);
        PointF endPoint = new PointF(endX,endY);

//        PointF controlPoint = new PointF(mRootRelativeLayout.getWidth() - UIUtils.dp2px(this,100),UIUtils.dp2px(this,100));
        PointF controlPoint = new PointF((startX + endX)/2,startY);

        doAnim(imageView,startPoint,endPoint,controlPoint);
    }

    /**
     * 开始做动画
     */
    private void doAnim(final ImageView imageView, PointF startPoint, PointF endPoint,PointF controlPoint) {

        ValueAnimator valueAnimator = ObjectAnimator.ofObject(new BezierEvaluator(controlPoint), startPoint, endPoint);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF curPoint = (PointF) animation.getAnimatedValue();
                imageView.setTranslationX(curPoint.x);
                imageView.setTranslationY(curPoint.y);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mRootRelativeLayout.removeView(imageView);
                count ++;
                mCountTextView.setText(count + "");
            }
        });
        valueAnimator.start();
    }


    public class BezierViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;
        public TextView mBuyTextView;
        public BezierViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.iv_img);
            mBuyTextView = (TextView) itemView.findViewById(R.id.tv_buy);
        }
    }


    /**
     * 贝赛尔曲线估值器
     */
    public class BezierEvaluator implements TypeEvaluator<PointF> {

        /* 控制点坐标 */
        private PointF mControlPoint;

        public BezierEvaluator(PointF controlPoint) {
            mControlPoint = controlPoint;
        }

        @Override
        public PointF evaluate(float v, PointF pointF, PointF t1) {
            return BezierUtil.calculateBezierPointForQuadratic(v, pointF, mControlPoint, t1);
        }
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
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
}

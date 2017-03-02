package com.org.sleepgod.widget.numAnimView;

import android.animation.TypeEvaluator;

/**
 * Created by cool on 2017/2/28.
 */

public class CustomPointEvaluator implements TypeEvaluator<CustomPoint> {
    @Override
    public CustomPoint evaluate(float fraction, CustomPoint startValue, CustomPoint endValue) {
        float x = startValue.getX() + fraction*(endValue.getX() - startValue.getX());
        float y = startValue.getY() + fraction*(endValue.getY() - startValue.getY());
        return new CustomPoint(x,y);
    }
}

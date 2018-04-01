package com.gdut.dkmfromcg.ojbk_ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by dkmFromCG on 2018/3/15.
 * function:
 */

public class NotSlideViewPager extends ViewPager {

    boolean isSlide = false;
    public NotSlideViewPager(Context context) {
        super(context);
    }
    public NotSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (isSlide) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isSlide) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }
}

package com.sds.study.recoderapp.record;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 뷰페이저는 무조건 슬라딩 되므로, 그기능을 막아버리자..
 */

public class MyViewPager extends ViewPager{
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

/*    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }*/
}

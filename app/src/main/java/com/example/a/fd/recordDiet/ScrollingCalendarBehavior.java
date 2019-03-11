package com.example.a.fd.recordDiet;

/**
 * @Package: com.example.a.fd.recordDiet
 * @ClassName: ScrollingCalendarBehavior
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/27 22:45
 */
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

@SuppressWarnings("unused")
public class ScrollingCalendarBehavior extends AppBarLayout.Behavior {

    public ScrollingCalendarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return false;/*super.onInterceptTouchEvent(parent, child, ev);*/
    }
}

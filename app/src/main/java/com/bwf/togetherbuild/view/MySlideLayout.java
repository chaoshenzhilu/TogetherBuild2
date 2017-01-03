package com.bwf.togetherbuild.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Fanhy on 2016/11/14.
 * description：应用侧滑布局
 */

public class MySlideLayout extends SlidingPaneLayout{
	private float mInitialMotionX;
    private float mInitialMotionY;
    private float mEdgeSlop;
    
	public MySlideLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		ViewConfiguration config = ViewConfiguration.get(context);
        mEdgeSlop = config.getScaledEdgeSlop();
	}
	
	/**
	 * 重写事件拦截方法，解决和ViewPager的冲突
	 * 
	 * */
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = ev.getX();
                mInitialMotionY = ev.getY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final float x = ev.getX();
                final float y = ev.getY();
                // The user should always be able to "close" the pane, so we only check
                // for child scrollability if the pane is currently closed.
                if (mInitialMotionX > mEdgeSlop && !isOpen() && canScroll(this, false,
                        Math.round(x - mInitialMotionX), Math.round(x), Math.round(y))) {

                    // How do we set super.mIsUnableToDrag = true?

                    // send the parent a cancel event
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                    return super.onInterceptTouchEvent(cancelEvent);
                }
            }
        }

        return super.onInterceptTouchEvent(ev);
    }
}

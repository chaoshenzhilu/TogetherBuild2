package com.bwf.togetherbuild.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * Created by Fanhy on 2016/11/14.
 * description：无滑动GridView
 */
public class NonScrollGridView extends GridView{

    public NonScrollGridView(Context context, AttributeSet attrs){
         super(context, attrs);
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
         int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
         super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}

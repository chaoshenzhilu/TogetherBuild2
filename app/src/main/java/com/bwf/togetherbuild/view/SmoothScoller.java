package com.bwf.togetherbuild.view;

import android.content.Context;
import android.widget.Scroller;
/**
 * Created by Fanhy on 2016/11/14.
 * description：Scroller
 */
public class SmoothScoller extends Scroller{

	public SmoothScoller(Context context) {
		super(context);
	}
	
	@Override
	public void startScroll(int startX, int startY, int dx, int dy, int duration) {
		super.startScroll(startX, startY, dx, dy, 1000);
	}
}

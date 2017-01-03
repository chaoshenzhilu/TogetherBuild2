package com.bwf.togetherbuild.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * Created by Fanhy on 2016/11/14.
 * description：复写父类的layoutChildren，在通知更新后调用该方法进行ListView的绘制
 */
public class SortListView extends ListView{

	public SortListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 调用父类的layoutChildren
	 * */
	@Override
	public void layoutChildren() {
		super.layoutChildren();
	}
}

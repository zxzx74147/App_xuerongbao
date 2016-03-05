package com.zxzx74147.devlib.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollControlViewPager extends ViewPager {
	private boolean scrollable = true;

	public ScrollControlViewPager(Context context) {
		super(context);
	}

	public ScrollControlViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScrollable(boolean enable) {
		scrollable = enable;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (scrollable) {
			return super.onInterceptTouchEvent(event);
		} else {
			return false;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (scrollable) {
			return super.onTouchEvent(event);
		} else {
			return false;
		}
	}
}

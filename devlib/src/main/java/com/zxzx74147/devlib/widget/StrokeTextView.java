package com.zxzx74147.devlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.zxzx74147.devlib.R;


public class StrokeTextView extends TextView {
//    public int mOuterColor = Color.BLACK;
//    public int mInnerColor = Color.WHITE;
	public int mOuterColor;
	public int mInnerColor;
	private float mWidth ;
    
	public StrokeTextView(Context context) {
		super(context);
	}

	public StrokeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (attrs != null) {
			TypedArray attsArray = context.obtainStyledAttributes(attrs,
					R.styleable.StrokeTextView);
			mWidth = attsArray.getDimension(R.styleable.StrokeTextView_stroke_width, getResources().getDimension(R.dimen.stroke_width));
			mOuterColor = attsArray.getColor(R.styleable.StrokeTextView_stroke_color, getResources().getColor(R.color.black));
			mInnerColor = attsArray.getColor(R.styleable.StrokeTextView_inner_color,getResources().getColor(R.color.white));
			attsArray.recycle();
		}
	}

	public StrokeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
    public void setContentColor(int outerColor, int innerColor){
    	mOuterColor = outerColor;
    	mInnerColor = innerColor;
		invalidate();
    }
    
	@Override
	protected void onDraw(Canvas canvas) {
		Paint mPaint = getPaint();
		
		//step1 outer
//		float width = getResources().getDimension(R.dimen.stroke_width);
		mPaint.setStrokeWidth(mWidth);
		mPaint.setStyle(Paint.Style.STROKE);
		setTextColor(mOuterColor);

		super.onDraw(canvas);
		
		//step2 inner
		setTextColor(mInnerColor);
		mPaint.setStyle(Paint.Style.FILL);
		super.onDraw(canvas);	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		return false;
	}
}

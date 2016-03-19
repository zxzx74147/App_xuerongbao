package com.wazxb.xuerongbao.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.zxzx74147.devlib.ZXApplicationDelegate;

/**
 * Created by zhengxin on 16/3/19.
 */
public class PaybackBar extends View {

    private int mColorGrey = 0xff999999;
    private int mColorOrange = 0xffff4800;
    private int mColorDarkGrek = 0xff333333;
    private float factor = 0.5f;
    private int padding = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_20);
    private int mRadisSmall = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_6);
    private int mRadisBig = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_10);
    private int mLineWidth = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_3);
    private int mTextOffset = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_15);
    private Paint mPaint = new Paint();


    public PaybackBar(Context context) {
        super(context);
        init();
    }

    public PaybackBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaybackBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.default_size_20));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int progress = (int) (width * factor);
        mPaint.setColor(mColorGrey);
        canvas.drawLine(mRadisBig, mRadisBig, width - mRadisBig, mRadisBig, mPaint);
        canvas.drawCircle(width - mRadisBig, mRadisBig, mRadisSmall, mPaint);
        mPaint.setColor(mColorOrange);
        canvas.drawCircle(mRadisBig, mRadisBig, mRadisSmall, mPaint);
        canvas.drawCircle(progress - mRadisBig, mRadisBig, mRadisBig, mPaint);
        canvas.drawLine(mRadisBig, mRadisBig, progress - mRadisBig, mRadisBig, mPaint);
        mPaint.setColor(mColorGrey);
        canvas.drawText("0%", 0, mRadisBig + 20, mPaint);
        canvas.drawText("100%", width - mRadisBig - mTextOffset, mRadisBig + 20, mPaint);
        canvas.drawText((factor * 100) + "%", progress - mRadisBig - mTextOffset, mRadisBig + 20, mPaint);
    }

}

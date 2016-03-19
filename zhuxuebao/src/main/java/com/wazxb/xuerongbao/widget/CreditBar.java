package com.wazxb.xuerongbao.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.storage.data.LoanItemData;
import com.zxzx74147.devlib.ZXApplicationDelegate;

/**
 * Created by zhengxin on 16/3/19.
 */
public class CreditBar extends View {

    private int mColorGrey = 0xff999999;
    private int mColorOrange = 0xffff4800;
    private int mColorDarkGrek = 0xff333333;
    private float factor = 0.5f;
    private int padding = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_20);
    private int mRadisSmall = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_8);
    private int mRadisBig = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_13);
    private int mLineWidth = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_3);
    private int mTextOffset = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_15);
    private int mTextOffsetY = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_35);
    private Paint mPaint = new Paint();
    private float max = 5000;
    private float process;
    private LoanItemData loanData;


    public CreditBar(Context context) {
        super(context);
        init();
    }

    public CreditBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreditBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.default_size_20));
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int progress = 0;
        int current = (int) (process * 100 / max);
        if (max != 0) {
            progress = (int) (width * progress / max);
        }
        mPaint.setColor(mColorDarkGrek);
        canvas.drawLine(mRadisBig, mRadisBig, width - mRadisBig, mRadisBig, mPaint);
        canvas.drawCircle(width - mRadisBig, mRadisBig, mRadisSmall, mPaint);
        mPaint.setColor(mColorOrange);
        canvas.drawCircle(mRadisBig, mRadisBig, mRadisSmall, mPaint);
        canvas.drawCircle(progress + mRadisBig, mRadisBig, mRadisBig, mPaint);
        canvas.drawLine(mRadisBig, mRadisBig, progress, mRadisBig, mPaint);
        mPaint.setColor(mColorGrey);

    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public void setLoanData(LoanItemData loanData) {
        this.loanData = loanData;
        if (loanData.status == 2 || loanData.status == 4 && loanData.type == 1) {
            process = Float.valueOf(loanData.returnMonth);
            max = Float.valueOf(loanData.month);
        } else {
            process = Float.valueOf(loanData.returnMoney);
            max = Float.valueOf(loanData.money);

        }
        invalidate();
    }

    public void setProgress(int edit) {

    }
}

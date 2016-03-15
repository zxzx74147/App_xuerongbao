package com.wazxb.xuerongbao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by zhengxin on 16/2/27.
 */
public class CuntdownButton extends Button {
    private long mLastSuccTime = 0;
    private long mMinInvTime = 60 * 1000;
    private String mText = null;

    public CuntdownButton(Context context) {
        super(context);
    }

    public CuntdownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CuntdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void markSucc() {
        if (mText == null) {
            mText = getText().toString();
        }
        setEnabled(false);
        mLastSuccTime = System.currentTimeMillis();
        removeCallbacks(mCheckRunnable);
        post(mCheckRunnable);
    }

    private Runnable mCheckRunnable = new Runnable() {
        @Override
        public void run() {
            long now = System.currentTimeMillis();
            if (now - mLastSuccTime < mMinInvTime) {
                int last = (int) ((mMinInvTime - (now - mLastSuccTime)) / 1000);
                setText(last + "s重新获取");
                removeCallbacks(this);
                postDelayed(this, 1000);
            } else {
                setEnabled(true);
                setText(mText);
                mText = null;
            }
        }
    };


}

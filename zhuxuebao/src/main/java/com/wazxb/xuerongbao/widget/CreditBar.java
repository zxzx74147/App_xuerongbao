package com.wazxb.xuerongbao.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.databinding.CreditBarBinding;

/**
 * Created by zhengxin on 16/3/19.
 */
public class CreditBar extends RelativeLayout {

    private CreditBarBinding mBinding = null;

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
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.credit_bar, this, true);
    }


    public void setProgress(int edit) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.credit_seek, null);
        ((TextView) v.findViewById(R.id.credit)).setText(String.valueOf(edit));
        mBinding.progressBar.setThumb(getViewDrawble(v));
        mBinding.progressBar.setProgress(edit);
    }

    public static BitmapDrawable getViewDrawble(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        view.layout(0, 0, width, height);//very important
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        BitmapDrawable drawable = new BitmapDrawable(view.getDrawingCache());
        drawable.setBounds(0, 0, width, height);
        return drawable;

    }
}

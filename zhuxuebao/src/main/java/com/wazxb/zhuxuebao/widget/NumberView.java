package com.wazxb.zhuxuebao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wazxb.zhuxuebao.R;
import com.zxzx74147.devlib.ZXApplicationDelegate;

/**
 * Created by zhengxin on 16/3/3.
 */
public class NumberView extends LinearLayout {

    private static final int[] table = new int[]{R.drawable.index_num_0, R.drawable.index_num_1, R.drawable.index_num_2, R.drawable.index_num_3, R.drawable.index_num_4,
            R.drawable.index_num_5, R.drawable.index_num_6, R.drawable.index_num_7, R.drawable.index_num_8, R.drawable.index_num_9};
    private static final int PADDING = ZXApplicationDelegate.getApplication().getResources().getDimensionPixelSize(R.dimen.default_gap_7);
    public int mNumber = 0;

    public NumberView(Context context) {
        super(context);
        init();
    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }

    public void setNumber(int num) {
        if (mNumber == num) {
            return;
        }

        removeAllViews();
        while (num > 0) {
            int current = num % 10;
            num /= 10;
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(table[current]);
            imageView.setPadding(PADDING, 0, PADDING, 0);
            addView(imageView, 0);
        }

    }
}

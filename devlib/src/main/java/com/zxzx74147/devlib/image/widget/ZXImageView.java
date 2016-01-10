package com.zxzx74147.devlib.image.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by zhengxin on 15/8/27.
 */
public class ZXImageView extends RoundedImageView {

    private String mUrl = null;

    public ZXImageView(Context context) {
        super(context);
    }

    public ZXImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZXImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageUrl(String url) {
        mUrl = url;
    }

    public String getImageUrl() {
        return mUrl;
    }
}

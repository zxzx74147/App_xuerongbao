package com.zxzx74147.devlib.image.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.GenericDraweeView;

/**
 * Created by zhengxin on 15/8/27.
 */
public class ZXImageView extends GenericDraweeView {

    public ZXImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context);
        this.setHierarchy(hierarchy);
    }

    public ZXImageView(Context context) {
        super(context);
    }

    public ZXImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZXImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}

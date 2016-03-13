package com.wazxb.zhuxuebao.common.portrait.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by zhengxin on 14/10/24.
 */
public class PhotoImageView extends PhotoView {

    private Rect mSelected = null;

    public PhotoImageView(Context context) {
        super(context);
        init();
    }

    public PhotoImageView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }


    public PhotoImageView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init();
    }

    private void init() {
        mSelected = new Rect();
    }


    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mSelected.left = 0;
        mSelected.right = right - left;
        mSelected.top = 0;
        mSelected.bottom = bottom - top;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public Rect getFillRect() {
        return mSelected;
    }

}

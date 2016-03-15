package com.wazxb.xuerongbao.common.portrait.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.wazxb.xuerongbao.R;
import com.zxzx74147.devlib.utils.ZXBitmapUtil;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by zhengxin on 14/10/24.
 */
public class RectSelectImageView extends PhotoView {
    private static final int MASK_ALPHA = 89;// 35%

    private Rect mSelected = null;
    private Paint mPaint = null;
    private boolean isSelecting = false;
    private float mTopOffset = 0.5f;
    private Paint maskPaint = null;

    public RectSelectImageView(Context context) {
        super(context);
        init();
    }

    public RectSelectImageView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }


    public RectSelectImageView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init();
    }

    private void init() {
        mSelected = new Rect();
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        maskPaint = new Paint();
        maskPaint.setColor(Color.BLACK);
        maskPaint.setAlpha(MASK_ALPHA);
        setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void setTopOffset(float offset) {
        mTopOffset = offset;
        requestLayout();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int padding = getResources().getDimensionPixelSize(R.dimen.default_gap_10);
        int width = right - left;
        int height = bottom - top;
        mSelected.left = padding;
        mSelected.right = width - padding;
        mSelected.top = (int) ((height - width) * mTopOffset);
        mSelected.bottom = mSelected.top + width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelecting) {
            canvas.drawRect(0, 0, getWidth(), mSelected.top, maskPaint);
            canvas.drawRect(0, mSelected.top, mSelected.left, mSelected.bottom, maskPaint);
            canvas.drawRect(mSelected.right, mSelected.top, getWidth(), mSelected.bottom, maskPaint);
            canvas.drawRect(0, mSelected.bottom, getWidth(), getHeight(), maskPaint);
            canvas.drawRect(mSelected, mPaint);
        }
    }

    public Bitmap getSelectedImage() {
        Bitmap bm = Bitmap.createBitmap(mSelected.width(), mSelected.height(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bm);
        canvas.translate(-mSelected.left, -mSelected.top);
        boolean temp = isSelecting;
        isSelecting = false;
        draw(canvas);
        isSelecting = temp;
        bm = ZXBitmapUtil.resize(bm, 600);
        return bm;
    }

    @Override
    public Rect getFillRect() {
        return mSelected;
    }


    public void setIsSelecting(boolean b) {
        isSelecting = b;
        invalidate();
    }
}

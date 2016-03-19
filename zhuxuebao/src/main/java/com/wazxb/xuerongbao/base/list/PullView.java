package com.wazxb.xuerongbao.base.list;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.wazxb.xuerongbao.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.PtrUIHandlerHook;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class PullView extends ImageView implements PtrUIHandler {

    private AnimationDrawable mDrawable;
    private Drawable mImgDrawable = null;
    private float mScale = 1f;
    private PtrFrameLayout mPtrFrameLayout;
    private long mStartTime = System.currentTimeMillis();

    private Animation mScaleAnimation = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            mScale = 1f - interpolatedTime;
            mDrawable.setAlpha((int) (255 * mScale));
            invalidate();
        }
    };

    public PullView(Context context) {
        super(context);
        initView();
    }

    public PullView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PullView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setPtrFrameLayout(PtrFrameLayout layout) {

        final PtrUIHandlerHook mPtrUIHandlerHook = new PtrUIHandlerHook() {
            @Override
            public void run() {
                startAnimation(mScaleAnimation);
            }
        };

        mScaleAnimation.setDuration(200);
        mScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPtrUIHandlerHook.resume();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mPtrFrameLayout = layout;
        mPtrFrameLayout.setRefreshCompleteHook(mPtrUIHandlerHook);
    }

    private void initView() {
        int padding = getResources().getDimensionPixelSize(R.dimen.default_gap_30);
        setPadding(padding,padding,padding,padding);
        mDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.progress);
        mDrawable.setCallback(this);

        setScaleType(ScaleType.CENTER_INSIDE);
        setImageDrawable(mDrawable);
        mDrawable.start();
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (dr == mDrawable) {
            invalidate();
        } else {
            super.invalidateDrawable(dr);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = mDrawable.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int size = mDrawable.getIntrinsicHeight();
        mDrawable.setBounds(0, 0, size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int saveCount = canvas.save();
        Rect rect = mDrawable.getBounds();
        int l = getPaddingLeft() + (getMeasuredWidth() - mDrawable.getIntrinsicWidth()) / 2;
        canvas.translate(l, getPaddingTop());
        canvas.scale(mScale, mScale, rect.exactCenterX(), rect.exactCenterY());
        canvas.restoreToCount(saveCount);
    }

    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     *
     * @param frame
     */
    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mScale = 1f;
        mDrawable.stop();
    }

    /**
     * prepare for loading
     *
     * @param frame
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
    }

    /**
     * perform refreshing UI
     *
     * @param frame
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mDrawable.setAlpha(255);
        mDrawable.start();
    }

    /**
     * perform UI after refresh
     *
     * @param frame
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mDrawable.stop();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        float percent = Math.min(1f, ptrIndicator.getCurrentPercent());

        if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            mDrawable.setAlpha((int) (255 * percent));


            invalidate();
        }
    }
}

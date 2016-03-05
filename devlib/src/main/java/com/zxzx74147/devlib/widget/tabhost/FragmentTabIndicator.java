package com.zxzx74147.devlib.widget.tabhost;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxzx74147.devlib.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;


public class FragmentTabIndicator extends LinearLayout implements IFragmentTabIndicator {

    public int mTextColorResId;
    public int mTextDrawable;

    private TextView mContentTv;
    private View mDivider;

    private HashMap<String, FragmentTapTip> mTips = new HashMap<String, FragmentTapTip>();

    public FragmentTabIndicator(Context context) {
        super(context);
        init();
    }

    public FragmentTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setTextColorRes(int colorId) {
        if (colorId == 0) {
            return;
        }
        mTextColorResId = colorId;
        ColorStateList mColor = getContext().getResources().getColorStateList(
                colorId);
        mContentTv.setTextColor(mColor);
    }

    public void setTextDrawable(int drawable) {
        if (drawable == 0) {
            return;
        }
        mTextDrawable = drawable;
        Drawable mDrawable = getContext().getResources().getDrawable(drawable);
        mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(),
                mDrawable.getMinimumHeight());
        mContentTv.setCompoundDrawables(null, mDrawable, null, null);
        mContentTv.setCompoundDrawablePadding(getContext().getResources().getDimensionPixelSize(R.dimen.default_gap_3));
    }

    private void init() {

        mContentTv = new TextView(getContext());
//        mContentTv.setPadding(0,0,0, TbadkApplication.getInst().getResources().getDimensionPixelSize(R.dimen.default_gap_8));
        mContentTv.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mContentTv.setGravity(Gravity.CENTER);
        setTextColorRes(mTextColorResId);
        setTextDrawable(mTextDrawable);
        setPadding(0, getContext().getResources().getDimensionPixelSize(R.dimen.default_gap_4), 0, 0);
        mContentTv.setDuplicateParentStateEnabled(true);
        mContentTv.setPadding(0, getResources().getDimensionPixelSize(R.dimen.default_gap_15), 0, 0);
        addView(mContentTv);
    }

    public void center() {
        setPadding(0, 0, 0, 0);
        mContentTv.setPadding(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Set<Entry<String, FragmentTapTip>> entryset = mTips.entrySet();
        Iterator<Entry<String, FragmentTapTip>> iter = entryset.iterator();
        while (iter.hasNext()) {
            Entry<String, FragmentTapTip> entry = iter.next();
            FragmentTapTip tip = entry.getValue();
            tip.view.measure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Set<Entry<String, FragmentTapTip>> entryset = mTips.entrySet();
        Iterator<Entry<String, FragmentTapTip>> iter = entryset.iterator();

        while (iter.hasNext() && mContentTv.getText() != null) {
            Entry<String, FragmentTapTip> entry = iter.next();
            FragmentTapTip tip = entry.getValue();
            int width = tip.view.getMeasuredWidth();
            int height = tip.view.getMeasuredHeight();
            int l = 0;
            int t = 0;
            if (tip.isRight) {
                int textWidth = (int) mContentTv.getPaint().measureText(
                        mContentTv.getText().toString());
                l = getMeasuredWidth() / 2 + tip.offsetX + textWidth / 2 - 10;
                t = 8;
            } else {
                int textWidth = (int) mContentTv.getPaint().measureText(
                        mContentTv.getText().toString());
                l = getMeasuredWidth() / 2 - tip.offsetX - textWidth / 2;
                t = (getMeasuredHeight() - 6)
                        / 2 - tip.view.getMeasuredHeight() / 2;
            }
            tip.view.layout(l, t, l + width, t + height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void addTip(String key, FragmentTapTip tip) {
        if (tip.view != null) {
            addView(tip.view);
            mTips.put(key, tip);
        }
        invalidate();
    }

    public FragmentTapTip getTip(String key) {
        return mTips.get(key);
    }

    public FragmentTapTip removeTip(String key) {
        try {
            if (mTips.containsKey(key)) {
                this.removeView(mTips.get(key).view);
                return mTips.remove(key);
            }
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child != mDivider && child != mContentTv) {
                    this.removeView(child);
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    public boolean hasTip(String key) {
        return mTips.containsKey(key);
    }

    public void setText(int textId) {
        mContentTv.setText(textId);
    }

    public void setText(String text) {
        mContentTv.setText(text);
    }

    public void setTextSize(float textSize) {
        mContentTv.setTextSize(textSize);
    }

    public void setTextSize(int unit, float textSize) {
        mContentTv.setTextSize(unit, textSize);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    public void setTextColor(ColorStateList colorList) {
        mContentTv.setTextColor(colorList);
    }

    public static class FragmentTapTip {
        public View view;
        public boolean isRight = true;
        public int offsetX;
    }

    public void showDivider() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                1,
                LayoutParams.MATCH_PARENT);
        lp.topMargin = getResources().getDimensionPixelOffset(R.dimen.default_gap_30);
        lp.bottomMargin = lp.topMargin;
        mDivider = new View(getContext());
//        mDivider.setBackgroundColor(getContext().getResources().getColor(R.color.tab_divider));
        mDivider.setLayoutParams(lp);
        addView(mDivider, 0);
    }

    @Override
    public void setSelected(boolean isSelected) {
        mContentTv.setSelected(isSelected);
    }

    public boolean getIsSelected() {
        return true;
    }

    @Override
    public void showTip() {

    }

    @Override
    public void showTip(int num) {

    }

    @Override
    public void dismissTip() {

    }

    @Override
    public boolean hasTip() {
        return false;
    }

}

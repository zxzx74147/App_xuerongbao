package com.zxzx74147.devlib.widget.tabhost;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zxzx74147.devlib.R;


public class FragmentTabWidget extends LinearLayout {

    private static final int DEFAULT_PAINT_FLAGS = Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG;

    private int mSelectedTabIndex = -1;

    private OnTabSelectionChanged mSelectionChangedListener;

    private final Rect mCursorRect = new Rect();

    private final Rect mDviderRect = new Rect();

    private Paint mPaint = new Paint();

    private int mDividerHeight;

    private int mCursorHeight;

    private int mDividerColor;

    private int mCursorColor;

    private int mChildCount;

    private int left;

    private int mWidth;

    private int mHeight;

    private int mItemWidth;

    private int mTabMode = TabConfig.MODE_TOP;

    private int mCursorMode = TabConfig.CURSOR_MODE_TOP;

    private int mVerticalDividerWidth = 0;
    private int mVerticalDividerColor = 0;
    private int mVerticalDividerPadding = 0;

    private SparseArray<View> mTabMap = new SparseArray<View>();

    public FragmentTabWidget(Context context) {
        super(context);
        init();
    }

    public FragmentTabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        mPaint = new Paint(DEFAULT_PAINT_FLAGS);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        setWillNotDraw(false);
        mPaint.setColor(mDividerColor);
        mPaint.setColor(mCursorColor);
    }

    public void applayAttr(TypedArray attsArray) {
        if (attsArray == null) {
            return;
        }
        mTabMode = attsArray.getInt(R.styleable.FragmentTabHost_tabPosition, TabConfig.MODE_TOP);
        mCursorColor = attsArray.getColor(R.styleable.FragmentTabHost_cursorColor, 0);
        mCursorHeight = attsArray.getDimensionPixelSize(R.styleable.FragmentTabHost_cursorHeight, 0);
        mDividerColor = attsArray.getColor(R.styleable.FragmentTabHost_dividerColor, 0);
        mDividerHeight = attsArray.getDimensionPixelSize(R.styleable.FragmentTabHost_dividerHeight, 0);
        mCursorMode = attsArray.getInt(R.styleable.FragmentTabHost_cursorMode, TabConfig.CURSOR_MODE_TOP);
        mVerticalDividerPadding = attsArray.getDimensionPixelSize(R.styleable.FragmentTabHost_verticalDividerPadding, 0);
        mVerticalDividerWidth = attsArray.getDimensionPixelSize(R.styleable.FragmentTabHost_verticalDividerWidth, 0);
        mVerticalDividerColor = attsArray.getColor(R.styleable.FragmentTabHost_verticalDividerColor, 0);
        int mBackground = attsArray.getColor(R.styleable.FragmentTabHost_tabWigetBackground, 0);
        setBackgroundColor(mBackground);

    }

    public void setCurrentTab(int index, boolean changeCursor) {
        if (index >= mTabMap.size()) {
            index = 0;
        }
        if (index < 0 || index >= mTabMap.size()) {
            return;
        }
        if (mSelectedTabIndex == index) {
            return;
        }
        if (mSelectedTabIndex != -1) {
            mTabMap.get(mSelectedTabIndex).setSelected(false);
        }
        mSelectedTabIndex = index;
        mTabMap.get(mSelectedTabIndex).setSelected(true);
        if (changeCursor) {
            changeLeft(mSelectedTabIndex, 0);
        }
    }

    @Override
    public void addView(View child) {
        if (child == null) {
            return;
        }
        fixChild(child, 1.0f);
        child.setOnClickListener(new TabClickListener(getChildCount()));
        mTabMap.put(getChildCount(), child);
        super.addView(child);
        mChildCount++;
    }

    @Override
    public void addView(View child, int index) {
        if (child == null) {
            return;
        }
        fixChild(child, 1.0f);
        child.setOnClickListener(new TabClickListener(getChildCount()));
        mTabMap.put(getChildCount(), child);
        super.addView(child, index);
        mChildCount++;
    }

    public void addView(View child, float weight) {
        if (child == null) {
            return;
        }
        fixChild(child, weight);
        child.setOnClickListener(new TabClickListener(getChildCount()));
        mTabMap.put(getChildCount(), child);
        super.addView(child);
    }

    public void addView(View child, int index, float weight) {
        if (child == null) {
            return;
        }
        fixChild(child, weight);
        child.setOnClickListener(new TabClickListener(getChildCount()));
        mTabMap.put(getChildCount(), child);
        super.addView(child, index);
    }

    public void addFixWidthView(View child, int index, int width) {
        if (child == null) {
            return;
        }

        fixWidthChild(child, width);
        child.setOnClickListener(new TabClickListener(getChildCount()));
        mTabMap.put(getChildCount(), child);
        super.addView(child, index);
    }

    public View getViewByIndex(int index) {
        return mTabMap.get(index);
    }

    private void fixWidthChild(View child, int width) {
        LinearLayout.LayoutParams lp = new LayoutParams(
                width,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 0, 0);
        // lp.gravity= Gravity.CENTER;
        child.setLayoutParams(lp);
        child.setFocusable(true);
        child.setClickable(true);
    }

    // 安卓2.1兼容
    private void fixChild(View child, float weight) {
        if (child.getLayoutParams() == null) {
            LinearLayout.LayoutParams lp = new LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT, weight);
            lp.setMargins(0, 0, 0, 0);
            child.setLayoutParams(lp);
        }
        child.setFocusable(true);
        child.setClickable(true);
    }

    public void reset() {
        removeAllViews();
        mSelectedTabIndex = -1;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mChildCount == 0) {
            return;
        }
        mWidth = r - l;
        mHeight = b - t;
        mItemWidth = mWidth / mChildCount;
        if (mSelectedTabIndex != -1) {
            changeLeft(mSelectedTabIndex, 0);
        }
        layoutDvider();
    }

    private void layoutDvider() {
        int top = 0;
        if (mTabMode == TabConfig.MODE_TOP) {
            top = mHeight - mDividerHeight;
        }
        mDviderRect.set(0, top, mWidth, top + mDividerHeight);
    }

    public void changeLeft(int index, float position) {
        left = mItemWidth * index;
        left += (int) (mItemWidth * position);
        if (mCursorHeight <= 0) {
            return;
        }
        int top = 0;
        if (mCursorMode == TabConfig.CURSOR_MODE_BUTTOM) {
            top = mHeight - mCursorHeight;
            mCursorRect.set(left, top, left + mItemWidth, mHeight);
        } else {
            mCursorRect.set(left, top, left + mItemWidth, mCursorHeight);
        }
        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mPaint.setColor(mDividerColor);
        canvas.drawRect(mDviderRect, mPaint);
        mPaint.setColor(mCursorColor);
        canvas.drawRect(mCursorRect, mPaint);
        mPaint.setColor(mVerticalDividerColor);
        if(mVerticalDividerWidth>0&& mVerticalDividerColor!=0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int x = child.getRight();
                canvas.drawRect(x - mVerticalDividerWidth / 2, mVerticalDividerPadding,
                        x - mVerticalDividerWidth / 2 + mVerticalDividerWidth, getHeight() - mVerticalDividerPadding, mPaint);
            }
        }
    }

    public void setTabSelectionListener(OnTabSelectionChanged listener) {
        mSelectionChangedListener = listener;
    }

    private class TabClickListener implements OnClickListener {

        private final int mTabIndex;

        private TabClickListener(int tabIndex) {
            mTabIndex = tabIndex;
        }

        @Override
        public void onClick(View v) {
            mSelectionChangedListener.onTabSelectionChanged(mTabIndex, true);
        }
    }

    public static interface OnTabSelectionChanged {
        void onTabSelectionChanged(int tabIndex, boolean clicked);
    }

    public void setTabMode(int tabMode) {
        mTabMode = tabMode;
        layoutDvider();
    }


}

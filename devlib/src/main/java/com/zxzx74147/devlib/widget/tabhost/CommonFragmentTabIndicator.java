package com.zxzx74147.devlib.widget.tabhost;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxzx74147.devlib.R;
import com.zxzx74147.devlib.utils.ZXViewHelper;



/**
 * Created by zhengxin on 15/2/5.
 */
public class CommonFragmentTabIndicator extends FrameLayout implements IFragmentTabIndicator {

    private View mTip = null;
    private View mContent = null;
    private int mTipNum = -1;
    private Point mPoint = null;

    public CommonFragmentTabIndicator(Context context) {
        super(context);
        init();
    }

    public CommonFragmentTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonFragmentTabIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setForegroundGravity(Gravity.CENTER);
        setClipToPadding(false);
    }

    public void setOffset(int xOffset, int yOffset){
        mPoint = new Point(xOffset,yOffset);
    }

    public View setContentView(View content) {
        mContent = content;
        if(content.getLayoutParams()==null) {
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            addView(content, lp);
        }else{
            addView(content);
        }

        ZXViewHelper.dfsViewGroup(this, new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                view.setDuplicateParentStateEnabled(false);
                view.setClickable(false);
            }
        });
        return content;
    }

    public View setContentView(int id) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View content = inflater.inflate(id, null);
        return setContentView(content);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mTip != null) {
            mTip.measure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mTip != null){
            if( mContent != null) {
                if(mPoint == null) {
                    int contentWidth = mContent.getMeasuredWidth() - getPaddingTop() - getPaddingBottom();
                    int contentHeight = mContent.getMeasuredHeight() - getPaddingLeft() - getPaddingRight();
                    int frameWidth = this.getMeasuredWidth();
                    int frameHeight = this.getMeasuredHeight();
                    int width = mTip.getMeasuredWidth();
                    int height = mTip.getMeasuredHeight();
                    int l = ((frameWidth + contentWidth - width) >> 1) + getPaddingLeft();
                    int t = ((frameHeight - contentHeight - height) >> 1) + getPaddingTop();
                    mTip.layout(l, t, l + width, t + height);
                }else{
                    int contentWidth = mContent.getMeasuredWidth() - getPaddingTop() - getPaddingBottom();
                    int contentHeight = mContent.getMeasuredHeight() - getPaddingLeft() - getPaddingRight();
                    int width = mTip.getMeasuredWidth();
                    int height = mTip.getMeasuredHeight();
                    int l = ((contentWidth - width) >> 1) + getPaddingLeft()+mPoint.x;
                    int t = ((contentHeight - height) >> 1) + getPaddingTop()+mPoint.y;
                    mTip.layout(l, t, l + width, t + height);
                }
            }
        }
    }

    @Override
    public boolean getIsSelected() {
        return false;
    }

    @Override
    public void showTip() {
        if (mTipNum == 0) {
            dismissTip();
            return;
        }
        ImageView tipView = null;
        if (mTip != null) {
            if (mTip instanceof ImageView) {
                tipView = (ImageView) mTip;
            } else {
                dismissTip();
            }
        }
        if (tipView == null) {
            tipView = new ImageView(getContext());
            tipView.setBackgroundResource(0);
            tipView.setScaleType(ImageButton.ScaleType.CENTER);
            tipView.setImageResource(R.drawable.icon_remind_red_s_bg);
            addView(tipView);
        }
        mTip = tipView;
        requestLayout();
    }

    public void setTipNum(int tipNum){
        mTipNum = tipNum;
    }

    @Override
    public void showTip(int num) {
        if (mTipNum == num) {
            return;
        }
        if(num==0){
            dismissTip();
            return;
        }
        TextView tipView = null;
        if (mTip != null) {
            if (mTip instanceof TextView) {
                tipView = (TextView) mTip;
            } else {
                dismissTip();
            }
        }
        if (tipView == null) {
            tipView = new TextView(getContext());
            tipView.setGravity(Gravity.CENTER);
            tipView.setBackgroundResource(R.drawable.icon_remind_red_bg);
            tipView.setTextColor(getResources().getColor(R.color.white));
            tipView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.default_size_20));
            addView(tipView);
        }
        num = num>99? 99:num;
        tipView.setText(String.valueOf(num));
        mTip = tipView;
        requestLayout();
    }

    @Override
    public void dismissTip() {
        if(mTip==null){
            return;
        }
        removeView(mTip);
        mTipNum = -1;
        mTip = null;
    }

    public void setTip(View tip){
        mTip = tip;
    }

    @Override
    public boolean hasTip() {
        return mTip!=null;
    }
}

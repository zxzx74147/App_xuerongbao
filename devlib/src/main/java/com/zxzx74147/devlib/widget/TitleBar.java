package com.zxzx74147.devlib.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxzx74147.devlib.R;


/**
 * Created by zhengxin on 14-9-19.
 */
public class TitleBar extends LinearLayout {
    private ImageButton mBack = null;
    private TextView mTitle = null;
    private TextView mRightText = null;
    private LinearLayout mRight = null;
    private TextView mLeftText = null;
    private View mLeft = null;
    private View mRoot = null;
    private View mTitldDiv = null;

    public TitleBar(Context context) {
        super(context);
        init(null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mRoot = LayoutInflater.from(this.getContext()).inflate(R.layout.title_bar_layout, this);
        setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
        mBack = (ImageButton) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
        mRight = (LinearLayout) findViewById(R.id.right_box);
        mRightText = (TextView) findViewById(R.id.right_text);
        mLeft = findViewById(R.id.left_box);
        mLeftText = (TextView) findViewById(R.id.left_text);
        mTitldDiv = findViewById(R.id.title_divider);
        mBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        });
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.TitleBar, 0, 0);
            String title = a.getString(R.styleable.TitleBar_text);
            String rightText = a.getString(R.styleable.TitleBar_right_text);
            String leftText = a.getString(R.styleable.TitleBar_left_text);
            boolean has_text_title = a.getBoolean(R.styleable.TitleBar_text_title,true);

            if (has_text_title){
                mTitle.setVisibility(VISIBLE);
            }
            Drawable rightDrawable = a.getDrawable(R.styleable.TitleBar_right_drawable);
            boolean showBack = a.getBoolean(R.styleable.TitleBar_showBack, true);
            if (a.hasValue(R.styleable.TitleBar_bgColor)) {
                mRoot.setBackgroundColor(a.getColor(R.styleable.TitleBar_bgColor, 0));
            }
            if (a.hasValue(R.styleable.TitleBar_divColor)) {
                mTitldDiv.setBackgroundColor(a.getColor(R.styleable.TitleBar_divColor, 0));
            }
            mTitle.setText(title);
            mRightText.setText(rightText);
            mLeftText.setText(leftText);
            if (!showBack) {
                hideBack();
            }
            if (rightDrawable != null) {
                mRight.removeAllViews();
                ImageView image = new ImageView(getContext());
                image.setId(R.id.right_image);
                mRight.addView(image);
                image.setPadding(getResources().getDimensionPixelSize(R.dimen.default_gap_15),0,getResources().getDimensionPixelSize(R.dimen.default_gap_15),0);
                image.setImageDrawable(rightDrawable);
            }
            a.recycle();
        }
    }

    public void setText(int id) {
        mTitle.setText(id);
    }

    public void setText(String text) {
        mTitle.setVisibility(VISIBLE);
        mTitle.setText(text);
    }

    public void setRightText(int id) {
        mRightText.setText(id);
    }

    public void setRightDrawable(int id) {
        mRight.removeAllViews();
        ImageView image = new ImageView(getContext());
        image.setId(R.id.right_image);
        mRight.addView(image);
        image.setPadding(getResources().getDimensionPixelSize(R.dimen.default_gap_15),0,getResources().getDimensionPixelSize(R.dimen.default_gap_15),0);
        image.setImageResource(id);
    }

    public void setRightText(String text) {
        mRightText.setText(text);
    }

    public void setRightTextEnable(boolean enable) {
        mRightText.setEnabled(enable);
        mRight.setClickable(enable);
    }

    public void setRightClickListener(OnClickListener listener) {
        mRight.setOnClickListener(listener);
    }

    public void setLeftClickListener(OnClickListener listener) {
        mLeft.setOnClickListener(listener);
    }

    public ImageButton getBackView(){
        return mBack;
    }

    public View getRightView() {
        return mRight;
    }

    public TextView getRightTextView() {
        return mRightText;
    }

    public TextView getLeftTextView() {
        return mLeftText;
    }

    public void setBackClickListener(OnClickListener listener) {
        mBack.setOnClickListener(listener);
    }

    public void hideBack() {
        mBack.setVisibility(View.GONE);

    }

    public void showBack() {
        mBack.setVisibility(View.VISIBLE);
    }

    public void hideRight() {
        mRight.setVisibility(View.GONE);
    }

    public void hideLeft() {
        mLeft.setVisibility(View.GONE);
    }

    public void showRight() {
        mRight.setVisibility(View.VISIBLE);
    }

    public void setRightTextColor(int color) {
        mRightText.setTextColor(color);
    }
}

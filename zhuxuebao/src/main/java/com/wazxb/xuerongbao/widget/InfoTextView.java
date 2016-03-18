package com.wazxb.xuerongbao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.databinding.InfoTextLayoutBinding;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/3/6.
 */
public class InfoTextView extends LinearLayout {


    private InfoTextLayoutBinding mBinding = null;

    public InfoTextView(Context context) {
        super(context);
        init(null);
    }

    public InfoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public InfoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(this.getContext()), R.layout.info_text_layout, this, true);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.InfoTextView, 0, 0);
            String label = a.getString(R.styleable.InfoTextView_info_label_text);
            if (ZXStringUtil.checkString(label)) {
                mBinding.label.setText(label);
            }
            String content = a.getString(R.styleable.InfoTextView_content_text);
            mBinding.content.setText(content);
            int drawable = a.getResourceId(R.styleable.InfoTextView_info_left_drawable, 0);
            if (drawable != 0) {
                mBinding.iconImg.setImageResource(drawable);
            } else {
                mBinding.iconImg.setVisibility(GONE);
            }

            int contentDrawable = a.getResourceId(R.styleable.InfoTextView_info_content_drawable, 0);
            if (contentDrawable != 0) {
                mBinding.icon.setImageResource(contentDrawable);
            }
            boolean showRight = a.getBoolean(R.styleable.InfoTextView_info_show_right, true);
            mBinding.rightImg.setVisibility(showRight ? VISIBLE : GONE);
            a.recycle();
        }

    }

    public void setContent(String edit) {
        mBinding.content.setText(edit);
    }

    public void setRemind(String num) {
        mBinding.infoRemind.setText(num);
        if (ZXStringUtil.checkString(num)) {
            mBinding.infoRemind.setVisibility(View.VISIBLE);
        } else {
            mBinding.infoRemind.setVisibility(View.GONE);
        }
    }

    public void setRightDrawable(Drawable drawable) {
        mBinding.icon.setImageDrawable(drawable);
    }
}

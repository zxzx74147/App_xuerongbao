package com.wazxb.zhuxuebao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.InfoTextLayoutBinding;
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

            a.recycle();
        }

    }
}

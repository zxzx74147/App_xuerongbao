package com.wazxb.zhuxuebao.util;

import android.databinding.BindingAdapter;

import com.wazxb.zhuxuebao.widget.InputTextView;
import com.wazxb.zhuxuebao.widget.UploadImageView;

/**
 * Created by zhengxin on 16/3/6.
 */
public class ZXBDataBindingUtil {

    @BindingAdapter("app:input_edit_text")
    public static void setLayoutHeight(InputTextView view, String edit) {
        view.setContent(edit);
    }

    @BindingAdapter("app:img_url")
    public static void setLayoutHeight(UploadImageView view, String url) {
        view.setImageUrl(url);
    }
}

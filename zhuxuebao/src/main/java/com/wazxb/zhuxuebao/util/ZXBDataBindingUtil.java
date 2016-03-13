package com.wazxb.zhuxuebao.util;

import android.databinding.BindingAdapter;

import com.wazxb.zhuxuebao.storage.data.ProdData;
import com.wazxb.zhuxuebao.widget.CashView;
import com.wazxb.zhuxuebao.widget.InfoTextView;
import com.wazxb.zhuxuebao.widget.InputTextView;
import com.wazxb.zhuxuebao.widget.UploadImageView;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/3/6.
 */
public class ZXBDataBindingUtil {

    @BindingAdapter("app:input_edit_text")
    public static void setLayoutHeight(InputTextView view, String edit) {
        view.setContent(edit);
    }

    @BindingAdapter("app:info_edit_text")
    public static void setLayoutHeight(InfoTextView view, String edit) {
        view.setContent(edit);
    }

    @BindingAdapter("app:info_remind_text")
    public static void setLayoutHeight(InfoTextView view, int num) {
        view.setRemind(num);
    }

    @BindingAdapter("app:input_sex")
    public static void setLayoutHeight(InputTextView view, int sex) {
        if (sex == 1) {
            view.setContent("男");
        } else if (sex == 2) {
            view.setContent("女");
        } else {
            view.setContent("");
        }
    }

    @BindingAdapter("app:img_url")
    public static void setLayoutHeight(UploadImageView view, String url) {
        view.setImageUrl(url);
    }

    public static String splitUrl(String urls, int index) {
        if (!ZXStringUtil.checkString(urls)) {
            return urls;
        }
        String[] url = urls.split(",");
        if (url.length > index) {
            return url[index];
        }
        if (index == 0) {
            return urls;
        }
        return null;
    }

    @BindingAdapter("app:prodData")
    public static void setLayoutHeight(CashView view, ProdData prod) {
        view.setProd(prod);
    }
}

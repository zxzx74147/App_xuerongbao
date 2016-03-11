package com.wazxb.zhuxuebao.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.zxzx74147.devlib.ZXApplicationDelegate;

/**
 * Created by zhengxin on 16/3/7.
 */
public class ZXBStringUtil {
    public static SpannableString appendDrawable(String text, int drawable) {
        int len = text.length();
        SpannableString ss = new SpannableString(text + " ");
        ImageSpan img = new ImageSpan(ZXApplicationDelegate.getApplication(), drawable);
        ss.setSpan(img, len, len + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public static SpannableString prependDrawable(String text, int drawable) {
        int len = text.length();
        SpannableString ss = new SpannableString(" " + text);
        ImageSpan img = new ImageSpan(ZXApplicationDelegate.getApplication(), drawable);
        ss.setSpan(img, 0, 0 + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}

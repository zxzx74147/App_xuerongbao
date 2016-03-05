package com.wazxb.zhuxuebao.util;

import android.view.View;
import android.view.ViewGroup;

import com.wazxb.zhuxuebao.widget.InputTextView;
import com.wazxb.zhuxuebao.widget.UploadImageView;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/2/27.
 */
public class CheckUtil {
    public static String checkDone(View view) {
        if (view == null) {
            return "";
        }
        if (view instanceof InputTextView) {
            if (!((InputTextView) view).isReady()) {
                return ((InputTextView) view).getError();
            }

        }
        if (view instanceof UploadImageView) {
            if (!((UploadImageView) view).checkDone()) {
                return ((UploadImageView) view).getError();
            }
        }
        if (view instanceof ViewGroup) {
            int childNum = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childNum; i++) {
                String error = checkDone(((ViewGroup) view).getChildAt(i));
                if (ZXStringUtil.checkString(error)) {
                    return error;
                }
            }
        }
        return "";
    }


}

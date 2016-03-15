package com.wazxb.xuerongbao.util;

import android.view.View;

import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.widget.InputTextView;
import com.wazxb.xuerongbao.widget.UploadImageView;
import com.zxzx74147.devlib.utils.ZXStringUtil;
import com.zxzx74147.devlib.utils.ZXViewHelper;

import java.util.HashMap;

/**
 * Created by zhengxin on 16/3/6.
 */
public class FillRqeustUtil {
    private static boolean mLoginOk = true;

    public static boolean checkFill(final ZXBBaseActivity activity) {
        mLoginOk = true;
        ZXViewHelper.dfsViewGroup(activity.getWindow().getDecorView(), new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    if (((InputTextView) view).isNotNull() && !((InputTextView) view).getIsFilled()) {
                        activity.showToast(((InputTextView) view).getError());
                        mLoginOk = false;
                    }
                } else if (view instanceof UploadImageView) {
                    if (((UploadImageView) view).isNotNull() && !((UploadImageView) view).getIsFilled()) {
                        activity.showToast(((UploadImageView) view).getError());
                        mLoginOk = false;
                    }
                }
            }
        });
        return mLoginOk;
    }

    //填充字段
    public static void fillRequest(final ZXBHttpRequest request, View mRoot) {
        final HashMap<String, Integer> mMap = new HashMap<>();
        ZXViewHelper.dfsViewGroup(mRoot, new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    InputTextView input = (InputTextView) view;
                    if (ZXStringUtil.checkString(input.getKey()) && ZXStringUtil.checkString(input.getText())) {
                        request.addParams(input.getKey(), input.getText());
                    }
                } else if (view instanceof UploadImageView) {
                    UploadImageView input = (UploadImageView) view;
                    if (input.getIsFilled()) {
                        Integer time = mMap.get(input.getPostKey());
                        if (time == null) {
                            time = 0;
                        }
                        time++;
                        mMap.put(input.getPostKey(), time);
                        String value = (String) request.getParams(input.getPostKey());
                        if (!ZXStringUtil.checkString(value)) {
                            value = "";
                        }
                        if (time > 1) {
                            value += ",";
                        }
                        value += input.getPicKey() == null ? "" : input.getPicKey();
                        if (ZXStringUtil.checkString(value)) {
                            request.addParams(input.getPostKey(), value);
                        }
                    }
                }
            }
        });
    }
}

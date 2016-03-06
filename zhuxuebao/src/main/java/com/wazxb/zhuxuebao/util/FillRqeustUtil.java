package com.wazxb.zhuxuebao.util;

import android.view.View;

import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.widget.InputTextView;
import com.wazxb.zhuxuebao.widget.UploadImageView;
import com.zxzx74147.devlib.utils.ZXStringUtil;
import com.zxzx74147.devlib.utils.ZXViewHelper;

import java.util.HashMap;

/**
 * Created by zhengxin on 16/3/6.
 */
public class FillRqeustUtil {

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
                    if (ZXStringUtil.checkString(input.getPostKey())) {
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

package com.wazxb.xuerongbao.util;

import android.text.Editable;
import android.text.TextWatcher;
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

    public interface CheckFilledListener {
        void onChecked(boolean isReady);
    }

    public static void addWatcher(final ZXBBaseActivity activity, final CheckFilledListener listener) {
        final TextWatcher mWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onChecked(checkFillSlience(activity));
            }
        };
        ZXViewHelper.dfsViewGroup(activity.getWindow().getDecorView(), new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    ((InputTextView) view).addTextChanged(mWatcher);
                }
            }
        });
    }

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


    public static boolean checkFillSlience(final ZXBBaseActivity activity) {
        mLoginOk = true;
        ZXViewHelper.dfsViewGroup(activity.getWindow().getDecorView(), new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    if (((InputTextView) view).isNotNull() && !((InputTextView) view).getIsFilled()) {

                        mLoginOk = false;
                    }
                } else if (view instanceof UploadImageView) {
                    if (((UploadImageView) view).isNotNull() && !((UploadImageView) view).getIsFilled()) {
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

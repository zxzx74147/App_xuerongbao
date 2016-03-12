package com.zxzx74147.devlib.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zxzx74147.devlib.callback.ICommonCallback;

/**
 * Created by zhengxin on 16/1/10.
 */
public class ZXViewUtil {


    public static void setBackgroundWithPadding(View view, int resourceId) {
        int left = view.getPaddingLeft();
        int right = view.getPaddingRight();
        int top = view.getPaddingTop();
        int bottom = view.getPaddingBottom();
        view.setBackgroundResource(resourceId);
        view.setPadding(left, top, right, bottom);

    }

    public static void setBackgroundWithPadding(View view, Drawable drawable) {
        int left = view.getPaddingLeft();
        int right = view.getPaddingRight();
        int top = view.getPaddingTop();
        int bottom = view.getPaddingBottom();
        view.setBackgroundDrawable(drawable);
        view.setPadding(left, top, right, bottom);

    }

    public static void progressView(View view, ICommonCallback<View> callback) {
        if (view == null || callback == null) {
            return;
        }

        callback.callback(view);
        if (view instanceof ViewGroup) {
            int size = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < size; i++) {
                View child = ((ViewGroup) view).getChildAt(i);
                progressView(view, callback);
            }
        }
    }

    public static void shoeSoftkeyBoard(EditText text) {

    }


}

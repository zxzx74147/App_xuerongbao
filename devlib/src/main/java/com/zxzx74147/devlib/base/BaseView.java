package com.zxzx74147.devlib.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseView<T> {
    protected View mRootView = null;
    protected Context mContext = null;
    protected View.OnClickListener mOutterOnClickListener = null;

    protected View.OnLongClickListener mOutterOnLongClickListener = null;

    protected T mData;

    public BaseView(Context context, int id) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        mRootView = inflater.inflate(id, null);
    }

    public BaseView(Context context, View root) {
        mContext = context;
        mRootView = root;
    }

    public View getRootView() {
        return mRootView;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mOutterOnClickListener = listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        mOutterOnLongClickListener = listener;
    }

    public boolean hasParent() {
        if (mRootView.getParent() != null) {
            return true;
        }
        return false;
    }

    public void detechFromParent() {
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
    }

    public void setData(T data) {
        mData = data;
    }

}

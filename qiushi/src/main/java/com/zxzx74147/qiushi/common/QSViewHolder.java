package com.zxzx74147.qiushi.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhengxin on 15/9/6.
 */
public abstract class QSViewHolder<T> extends RecyclerView.ViewHolder {
    protected ViewDataBinding mBinding;
    public QSViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        mBinding = binding;

    }
    public QSViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void bindData(T data);

}

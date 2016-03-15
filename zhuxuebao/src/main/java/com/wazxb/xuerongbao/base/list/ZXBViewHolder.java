package com.wazxb.xuerongbao.base.list;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhengxin on 15/9/6.
 */
public abstract class ZXBViewHolder<T> extends RecyclerView.ViewHolder {
    protected ViewDataBinding mBinding;
    public ZXBViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        mBinding = binding;

    }
    public ZXBViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void bindData(T data);

}

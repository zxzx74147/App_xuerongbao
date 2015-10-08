package com.zxzx74147.qiushi.module.frs;

import android.databinding.ViewDataBinding;

import com.zxzx74147.qiushi.BR;
import com.zxzx74147.qiushi.common.QSViewHolder;
import com.zxzx74147.qiushi.common.data.CardItemData;
import com.zxzx74147.qiushi.databinding.FrsItemLayoutBinding;

/**
 * Created by zhengxin on 15/9/7.
 */
public class FrsCardViewHolder extends QSViewHolder<CardItemData> {

    public FrsCardViewHolder(ViewDataBinding binding) {
        super(binding);
    }

    @Override
    protected void bindData(CardItemData data) {
        FrsItemLayoutBinding binding = (FrsItemLayoutBinding)mBinding;
        binding.setVariable(BR.item, data);
        binding.executePendingBindings();
    }
}

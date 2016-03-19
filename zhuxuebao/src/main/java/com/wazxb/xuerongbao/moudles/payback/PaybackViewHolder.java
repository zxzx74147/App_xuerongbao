package com.wazxb.xuerongbao.moudles.payback;

import android.databinding.ViewDataBinding;

import com.wazxb.xuerongbao.base.list.ZXBViewHolder;
import com.wazxb.xuerongbao.databinding.ItemPaybackLayoutBinding;
import com.wazxb.xuerongbao.storage.data.LoanItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class PaybackViewHolder extends ZXBViewHolder<LoanItemData> {

    public PaybackViewHolder(ViewDataBinding binding) {
        super(binding);
    }

    @Override
    protected void bindData(final LoanItemData data) {
        final ItemPaybackLayoutBinding binding = (ItemPaybackLayoutBinding) mBinding;
        binding.setLoanData(data);
        binding.executePendingBindings();
    }
}

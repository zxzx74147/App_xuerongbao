package com.wazxb.zhuxuebao.moudles.payback;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.list.ZXBViewHolder;
import com.wazxb.zhuxuebao.databinding.ItemLoanHistoryLayoutBinding;
import com.wazxb.zhuxuebao.databinding.ItemLoanLayoutBinding;
import com.wazxb.zhuxuebao.storage.data.LoanHisData;
import com.wazxb.zhuxuebao.storage.data.LoanItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class PaybackViewHolder extends ZXBViewHolder<LoanItemData> {

    public PaybackViewHolder(ViewDataBinding binding) {
        super(binding);
    }

    @Override
    protected void bindData(LoanItemData data) {
        ItemLoanLayoutBinding binding = (ItemLoanLayoutBinding) mBinding;
        binding.setData(data);
        binding.executePendingBindings();
        binding.historyLayout.removeAllViews();
        if (data.hisList != null && data.hisList.loanHis != null) {
            for (LoanHisData his : data.hisList.loanHis) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ItemLoanHistoryLayoutBinding hisBinding = DataBindingUtil.inflate(LayoutInflater.from(mBinding.getRoot().getContext()), R.layout.item_loan_history_layout, null, false);
                binding.historyLayout.addView(hisBinding.getRoot(), lp);
                hisBinding.setData(his);
            }
        }
    }
}

package com.wazxb.xuerongbao.moudles.history;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.list.ZXBViewHolder;
import com.wazxb.xuerongbao.databinding.ItemLoanHistoryLayoutBinding;
import com.wazxb.xuerongbao.databinding.ItemLoanLayoutBinding;
import com.wazxb.xuerongbao.storage.data.LoanHisData;
import com.wazxb.xuerongbao.storage.data.LoanItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class HistoryViewHolder extends ZXBViewHolder<LoanItemData> {

    public HistoryViewHolder(ViewDataBinding binding) {
        super(binding);
    }

    @Override
    protected void bindData(final LoanItemData data) {
        final ItemLoanLayoutBinding binding = (ItemLoanLayoutBinding) mBinding;
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

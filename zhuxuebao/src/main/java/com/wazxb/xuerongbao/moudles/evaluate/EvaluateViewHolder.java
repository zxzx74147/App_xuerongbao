package com.wazxb.xuerongbao.moudles.evaluate;

import android.databinding.ViewDataBinding;

import com.wazxb.xuerongbao.base.list.ZXBViewHolder;
import com.wazxb.xuerongbao.databinding.ItemEvaluateLayoutBinding;
import com.wazxb.xuerongbao.storage.data.EvaluateItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class EvaluateViewHolder extends ZXBViewHolder<EvaluateItemData> {

    public EvaluateViewHolder(ViewDataBinding binding) {
        super(binding);
    }

    @Override
    protected void bindData(final EvaluateItemData data) {
        final ItemEvaluateLayoutBinding binding = (ItemEvaluateLayoutBinding) mBinding;
        binding.setData(data);
        binding.executePendingBindings();

    }
}

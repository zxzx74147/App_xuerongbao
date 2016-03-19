package com.wazxb.xuerongbao.moudles.common;

import android.databinding.ViewDataBinding;

import com.wazxb.xuerongbao.base.list.ZXBViewHolder;
import com.wazxb.xuerongbao.databinding.ItemSchoolBinding;
import com.wazxb.xuerongbao.storage.data.SchoolItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class SchoolViewHolder extends ZXBViewHolder<SchoolItemData> {

    public SchoolViewHolder(ViewDataBinding binding) {
        super(binding);
    }

    @Override
    protected void bindData(final SchoolItemData data) {
        final ItemSchoolBinding binding = (ItemSchoolBinding) mBinding;
        binding.setData(data);
        binding.executePendingBindings();

    }
}

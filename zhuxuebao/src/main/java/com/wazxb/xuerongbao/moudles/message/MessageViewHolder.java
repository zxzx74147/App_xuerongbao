package com.wazxb.xuerongbao.moudles.message;

import android.databinding.ViewDataBinding;

import com.wazxb.xuerongbao.base.list.ZXBViewHolder;
import com.wazxb.xuerongbao.databinding.ItemMessageBinding;
import com.wazxb.xuerongbao.storage.data.MessageItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class MessageViewHolder extends ZXBViewHolder<MessageItemData> {

    public MessageViewHolder(ViewDataBinding binding) {
        super(binding);
    }

    @Override
    protected void bindData(final MessageItemData data) {
        final ItemMessageBinding binding = (ItemMessageBinding) mBinding;
        binding.setData(data);
        binding.executePendingBindings();

    }
}

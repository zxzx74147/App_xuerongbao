package com.wazxb.xuerongbao.moudles.coin;

import android.databinding.ViewDataBinding;

import com.wazxb.xuerongbao.base.list.ZXBViewHolder;
import com.wazxb.xuerongbao.databinding.ItemCoinGiftBinding;
import com.wazxb.xuerongbao.storage.data.GiftItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class CoinGiftViewHolder extends ZXBViewHolder<GiftItemData> {

    public CoinGiftViewHolder(ViewDataBinding binding) {
        super(binding);
    }

    @Override
    protected void bindData(final GiftItemData data) {
        final ItemCoinGiftBinding binding = (ItemCoinGiftBinding) mBinding;
        binding.setData(data);
        binding.executePendingBindings();

    }
}

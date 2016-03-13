package com.wazxb.zhuxuebao.moudles.coin;

import com.wazxb.zhuxuebao.base.list.ZXBViewBinder;
import com.wazxb.zhuxuebao.databinding.ItemCoinGiftBinding;
import com.wazxb.zhuxuebao.storage.data.LoanItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class CoinGiftBinder extends ZXBViewBinder<LoanItemData> {

    public CoinGiftBinder() {
        registeItem(0, ItemCoinGiftBinding.class, CoinGiftViewHolder.class);
    }

    @Override
    public int getItemViewType(Object object) {
        return 0;
    }
}

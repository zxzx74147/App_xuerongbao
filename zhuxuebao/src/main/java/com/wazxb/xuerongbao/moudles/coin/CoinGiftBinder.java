package com.wazxb.xuerongbao.moudles.coin;

import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ItemCoinGiftBinding;
import com.wazxb.xuerongbao.storage.data.LoanItemData;

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

package com.wazxb.xuerongbao.moudles.payback;

import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ItemLoanLayoutBinding;
import com.wazxb.xuerongbao.storage.data.LoanItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class PaybackBinder extends ZXBViewBinder<LoanItemData> {

    public PaybackBinder() {
        registeItem(0, ItemLoanLayoutBinding.class, PaybackViewHolder.class);
    }

    @Override
    public int getItemViewType(Object object) {
        return 0;
    }
}

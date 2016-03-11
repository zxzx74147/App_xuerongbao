package com.wazxb.zhuxuebao.moudles.payback;

import com.wazxb.zhuxuebao.base.list.ZXBViewBinder;
import com.wazxb.zhuxuebao.databinding.ItemLoanLayoutBinding;
import com.wazxb.zhuxuebao.storage.data.LoanItemData;

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

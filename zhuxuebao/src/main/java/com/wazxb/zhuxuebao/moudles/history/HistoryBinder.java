package com.wazxb.zhuxuebao.moudles.history;

import com.wazxb.zhuxuebao.base.list.ZXBViewBinder;
import com.wazxb.zhuxuebao.databinding.ItemLoanLayoutBinding;
import com.wazxb.zhuxuebao.storage.data.LoanItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class HistoryBinder extends ZXBViewBinder<LoanItemData> {

    public HistoryBinder() {
        registeItem(0, ItemLoanLayoutBinding.class, HistoryViewHolder.class);
    }

    @Override
    public int getItemViewType(Object object) {
        return 0;
    }
}

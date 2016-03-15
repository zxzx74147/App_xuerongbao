package com.wazxb.xuerongbao.moudles.history;

import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ItemLoanLayoutBinding;
import com.wazxb.xuerongbao.storage.data.LoanItemData;

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

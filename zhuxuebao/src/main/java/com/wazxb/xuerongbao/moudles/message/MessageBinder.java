package com.wazxb.xuerongbao.moudles.message;

import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ItemMessageBinding;
import com.wazxb.xuerongbao.storage.data.LoanItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class MessageBinder extends ZXBViewBinder<LoanItemData> {

    public MessageBinder() {
        registeItem(0, ItemMessageBinding.class, MessageViewHolder.class);
    }

    @Override
    public int getItemViewType(Object object) {
        return 0;
    }
}

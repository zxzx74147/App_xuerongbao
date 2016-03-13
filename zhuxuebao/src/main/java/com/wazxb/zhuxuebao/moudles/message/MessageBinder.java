package com.wazxb.zhuxuebao.moudles.message;

import com.wazxb.zhuxuebao.base.list.ZXBViewBinder;
import com.wazxb.zhuxuebao.databinding.ItemMessageBinding;
import com.wazxb.zhuxuebao.storage.data.LoanItemData;

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

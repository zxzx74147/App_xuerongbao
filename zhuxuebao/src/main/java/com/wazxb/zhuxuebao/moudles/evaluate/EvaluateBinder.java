package com.wazxb.zhuxuebao.moudles.evaluate;

import com.wazxb.zhuxuebao.base.list.ZXBViewBinder;
import com.wazxb.zhuxuebao.databinding.ItemEvaluateLayoutBinding;
import com.wazxb.zhuxuebao.storage.data.LoanItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class EvaluateBinder extends ZXBViewBinder<LoanItemData> {

    public EvaluateBinder() {
        registeItem(0, ItemEvaluateLayoutBinding.class, EvaluateViewHolder.class);
    }

    @Override
    public int getItemViewType(Object object) {
        return 0;
    }
}

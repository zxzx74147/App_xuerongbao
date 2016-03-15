package com.wazxb.xuerongbao.moudles.evaluate;

import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ItemEvaluateLayoutBinding;
import com.wazxb.xuerongbao.storage.data.LoanItemData;

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

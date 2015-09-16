package com.zxzx74147.qiushi.module.frs;

import com.zxzx74147.qiushi.common.QSViewBinder;
import com.zxzx74147.qiushi.databinding.FrsItemLayoutBinding;

/**
 * Created by zhengxin on 15/9/7.
 */
public class FrsItemBinder extends QSViewBinder {

    public FrsItemBinder(){
        registeItem(0, FrsItemLayoutBinding.class,FrsCardViewHolder.class);
    }

    @Override
    public int getItemViewType(Object object) {
        return 0;
    }
}

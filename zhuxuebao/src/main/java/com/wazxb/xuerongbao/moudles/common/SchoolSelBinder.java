package com.wazxb.xuerongbao.moudles.common;

import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ItemSchoolBinding;
import com.wazxb.xuerongbao.storage.data.SchoolItemData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class SchoolSelBinder extends ZXBViewBinder<SchoolItemData> {

    public SchoolSelBinder() {
        registeItem(0, ItemSchoolBinding.class, SchoolViewHolder.class);
    }

    @Override
    public int getItemViewType(Object object) {
        return 0;
    }
}

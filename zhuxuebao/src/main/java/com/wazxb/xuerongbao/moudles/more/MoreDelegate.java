package com.wazxb.xuerongbao.moudles.more;

import com.wazxb.xuerongbao.R;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabStructure;

/**
 * Created by zhengxin on 16/2/20.
 */
public class MoreDelegate {

    public static FragmentTabStructure newInstance() {
        FragmentTabStructure structure = new FragmentTabStructure();
        structure.frag = new MoreFragment();
        structure.textResId = R.string.moudle_more;
        structure.textDrawable = R.drawable.maintab_more;

        return structure;
    }

}

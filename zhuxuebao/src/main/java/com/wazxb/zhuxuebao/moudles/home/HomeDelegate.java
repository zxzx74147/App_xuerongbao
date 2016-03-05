package com.wazxb.zhuxuebao.moudles.home;

import com.wazxb.zhuxuebao.R;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabStructure;

/**
 * Created by zhengxin on 16/2/20.
 */
public class HomeDelegate {

    public static FragmentTabStructure newInstance() {
        FragmentTabStructure structure = new FragmentTabStructure();
        structure.frag = new HomeFragment();
        structure.textResId = R.string.moudle_home;
        structure.textDrawable = R.drawable.maintab_home;

        return structure;
    }

}

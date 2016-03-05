package com.wazxb.zhuxuebao.moudles.personal;

import com.wazxb.zhuxuebao.R;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabStructure;

/**
 * Created by zhengxin on 16/2/20.
 */
public class PersonalDelegate {

    public static FragmentTabStructure newInstance() {
        FragmentTabStructure structure = new FragmentTabStructure();
        structure.frag = new PersonalFragment();
        structure.textResId = R.string.moudle_personal;
        structure.textDrawable = R.drawable.maintab_personal;

        return structure;
    }

}

package com.wazxb.xuerongbao.moudles.more;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityAboutBinding;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.InitData;

/**
 * Created by zhengxin on 16/3/6.
 */
public class AboutActivity extends ZXBBaseActivity {

    private ActivityAboutBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        InitData init = StorageManager.sharedInstance().getInitdat();
        mBinding.setHandler(this);
        mBinding.setData(init);
    }
}

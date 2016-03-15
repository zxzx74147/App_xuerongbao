package com.wazxb.xuerongbao.moudles.more;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityContractBinding;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.InitData;

/**
 * Created by zhengxin on 16/3/6.
 */
public class ContractActivity extends ZXBBaseActivity {

    private ActivityContractBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_contract);
        InitData init = StorageManager.sharedInstance().getInitdat();
        mBinding.setHandler(this);
        mBinding.setData(init);
    }
}

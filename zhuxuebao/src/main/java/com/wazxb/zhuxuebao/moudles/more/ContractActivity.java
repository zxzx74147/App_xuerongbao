package com.wazxb.zhuxuebao.moudles.more;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityContractBinding;
import com.wazxb.zhuxuebao.storage.StorageManager;
import com.wazxb.zhuxuebao.storage.data.InitData;

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

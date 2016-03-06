package com.wazxb.zhuxuebao.moudles.personal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityPersonalInfoBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.storage.data.UserAllData;

/**
 * Created by zhengxin on 16/3/6.
 */
public class PersonalInfoActivity extends ZXBBaseActivity {

    ActivityPersonalInfoBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info);
        mBinding.setHandler(this);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.user);
        }

    }

    public void onLogoutClick(View v) {
        AccountManager.sharedInstance().logout();
        finish();
    }
}

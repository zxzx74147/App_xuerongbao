package com.wazxb.zhuxuebao.moudles.credit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityCreditFamilyBinding;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.UidData;

public class CreditFamilyActivity extends ZXBBaseActivity {
    ActivityCreditFamilyBinding mBinding = null;
    ZXBHttpRequest<UidData> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit_family);

    }


}

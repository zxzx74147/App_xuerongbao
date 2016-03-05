package com.wazxb.zhuxuebao.moudles.credit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityCreditBinding;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;

/**
 * Created by zhengxin on 16/3/4.
 */
public class CreditActivity extends ZXBBaseActivity {

    private ActivityCreditBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit);
        mBinding.setHandler(this);
    }

    public void onCreditBaseClick(View v) {
        ZXActivityJumpHelper.startActivity(this, CreditBaseActivity.class);
    }

    public void onCreditHomeClick(View v) {
        ZXActivityJumpHelper.startActivity(this, CreditFamilyActivity.class);
    }

    public void onCreditContactsClick(View v) {
        ZXActivityJumpHelper.startActivity(this, CreditContactActivity.class);
    }

    public void onCreditConsumeClick(View v) {
        ZXActivityJumpHelper.startActivity(this, CreditOtherActivity.class);
    }

}

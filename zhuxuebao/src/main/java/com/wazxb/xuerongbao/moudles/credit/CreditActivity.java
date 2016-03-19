package com.wazxb.xuerongbao.moudles.credit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.EventBusConfig;
import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityCreditBinding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;

import de.greenrobot.event.EventBus;

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
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data);
            if (data.user.quotaTotal >= 5000) {
                mBinding.creditBg.setImageResource(R.drawable.credit_5000);
            } else if (data.user.quotaTotal >= 3000) {
                mBinding.creditBg.setImageResource(R.drawable.credit_3000);
            } else if (data.user.quotaTotal >= 1000) {
                mBinding.creditBg.setImageResource(R.drawable.credit_1000);
            } else if (data.user.quotaTotal >= 500) {
                mBinding.creditBg.setImageResource(R.drawable.credit_500);
            } else {
                mBinding.creditBg.setImageResource(R.drawable.credit_0);
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //取消EventBus
        EventBus.getDefault().unregister(this);
    }

    //事件1接收者：在主线程接收
    public void onEvent(String event) {
        if (EventBusConfig.EVENT_FRESH_USER_DATA.equals(event)) {
            UserAllData data = AccountManager.sharedInstance().getUserAllData();
            mBinding.setData(data);
        }
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

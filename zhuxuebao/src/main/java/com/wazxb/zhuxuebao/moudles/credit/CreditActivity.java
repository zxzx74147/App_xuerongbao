package com.wazxb.zhuxuebao.moudles.credit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityCreditBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
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
        Log.e("event", event);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        mBinding.setData(data);
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

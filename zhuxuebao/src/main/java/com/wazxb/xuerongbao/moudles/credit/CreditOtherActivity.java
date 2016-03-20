package com.wazxb.xuerongbao.moudles.credit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityCreditOtherBinding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.InitData;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.wazxb.xuerongbao.util.FillRqeustUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

public class CreditOtherActivity extends ZXBBaseActivity {
    ActivityCreditOtherBinding mBinding = null;
    ZXBHttpRequest<UserAllData> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit_other);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.cdLife);
        }
        InitData init = StorageManager.sharedInstance().getInitdat();
        if (init != null) {
            mBinding.setContract(init.contract);
        }
        init();
    }

    public void init() {
        mBinding.titleBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    public void submit() {
        if (mRequest != null) {
            mRequest.cancel();
            mRequest = null;
        }
        mRequest = new ZXBHttpRequest<>(UserAllData.class, new HttpResponseListener<UserAllData>() {
            @Override
            public void onResponse(HttpResponse<UserAllData> response) {
                hideProgressBar();
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().setUserAllData(response.result);
                AccountManager.sharedInstance().requestUserAllData();
                finish();
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_CD_LIFE);
        FillRqeustUtil.fillRequest(mRequest, getWindow().getDecorView());
        sendRequest(mRequest);
        showProgressBar();
    }


}

package com.wazxb.zhuxuebao.moudles.credit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityCreditFamilyBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
import com.wazxb.zhuxuebao.util.FillRqeustUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

public class CreditFamilyActivity extends ZXBBaseActivity {
    ActivityCreditFamilyBinding mBinding = null;
    ZXBHttpRequest<UserAllData> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit_family);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.cdHome);
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
                mRequest = null;
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().setUserAllData(response.result);
                finish();
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_CD_UPHOME);
        FillRqeustUtil.fillRequest(mRequest, getWindow().getDecorView());
        sendRequest(mRequest);

    }

}

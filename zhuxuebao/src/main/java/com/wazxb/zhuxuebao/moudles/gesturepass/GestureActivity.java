package com.wazxb.zhuxuebao.moudles.gesturepass;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityPasswordBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.UidData;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;

/**
 * Created by zhengxin on 16/3/7.
 */
public class GestureActivity extends ZXBBaseActivity {


    private ActivityPasswordBinding mBinding = null;
    private ZXBHttpRequest mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_password);
        mBinding.setHandler(this);
    }

    public void onGesturePassClick(View v) {
        ZXActivityJumpHelper.startActivity(this, GesturePasswordActivity.class, GesturePasswordActivity.MODE_SET);
    }

    public void onClearPassClick(View v) {
        submit("");
    }

    @Override
    public void onResume() {
        super.onResume();
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.user);
        } else {
            finish();
        }
    }

    public void submit(final String pass) {
        if (mRequest != null) {
            mRequest.cancel();
        }
        mRequest = new ZXBHttpRequest<>(UidData.class, new HttpResponseListener<UidData>() {
            @Override
            public void onResponse(HttpResponse<UidData> response) {
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().setPassword(pass);
                UserAllData data = AccountManager.sharedInstance().getUserAllData();
                if (data != null) {
                    mBinding.setData(data.user);
                }
            }
        });
        mRequest.addParams("gesture", pass);
        mRequest.setPath(NetworkConfig.ADDRESS_U_GESTURE);
        sendRequest(mRequest);
    }
}

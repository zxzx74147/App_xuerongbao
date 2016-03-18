package com.wazxb.xuerongbao.moudles.gesturepass;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityPasswordBinding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.UidData;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXDialogUtil;

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
        ZXDialogUtil.showCheckDialog(this, R.string.reset_pass_remind, new Runnable() {
            @Override
            public void run() {
                submit("");
            }
        });

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

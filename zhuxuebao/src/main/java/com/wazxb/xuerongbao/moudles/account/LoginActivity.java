package com.wazxb.xuerongbao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityLoginBinding;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.UidData;
import com.wazxb.xuerongbao.util.FillRqeustUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;

public class LoginActivity extends ZXBBaseActivity {
    private ActivityLoginBinding mBinding = null;
    private ZXBHttpRequest mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mBinding.setHandler(this);
        mBinding.titleBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZXActivityJumpHelper.startActivity(LoginActivity.this, RegitsterActivity.class);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (AccountManager.sharedInstance().hasUid()) {
            finish();
        }
    }


    public void onForgetClick(View view) {
        ZXActivityJumpHelper.startActivity(this, FindPasswordActivity.class);
    }

    private void checkContent() {
        String password = mBinding.passwordId.getText();
        String phoneNum = mBinding.phoneNumId.getText();
        mBinding.loginId.setEnabled(false);
        if (!ZXStringUtil.checkString(password)) {
            return;
        }
        if (!ZXStringUtil.checkString(phoneNum)) {
            return;
        }
        mBinding.loginId.setEnabled(true);
    }


    public void onLoginClick(View v) {
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

                AccountManager.sharedInstance().saveUid(response.result.uId);

                finish();
            }
        });
        FillRqeustUtil.fillRequest(mRequest, getWindow().getDecorView());
        mRequest.setPath(NetworkConfig.ADDRESS_U_LOGIN);
        sendRequest(mRequest);
    }
}

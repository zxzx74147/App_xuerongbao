package com.wazxb.zhuxuebao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alibaba.sdk.android.util.Md5Utils;
import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.ActivityLoginBinding;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.UidData;
import com.wazxb.zhuxuebao.widget.InputTextView;
import com.wazxb.zhuxuebao.widget.UploadImageView;
import com.zxzx74147.devlib.base.ZXBaseActivity;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;
import com.zxzx74147.devlib.utils.ZXViewHelper;

public class LoginActivity extends ZXBaseActivity {
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
        if(AccountManager.sharedInstance().hasUid()){
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
        ZXViewHelper.dfsViewGroup(getWindow().getDecorView(), new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    InputTextView input = (InputTextView) view;
                    if (ZXStringUtil.checkString(input.getKey())) {
                        if (input != mBinding.passwordId) {
                            mRequest.addParams(input.getKey(), input.getText());
                        } else {
                            mRequest.addParams(input.getKey(), Md5Utils.md5Digest(input.getText().getBytes()));
                        }
                    }
                } else if (view instanceof UploadImageView) {

                }
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_U_LOGIN);
        sendRequest(mRequest);
    }
}

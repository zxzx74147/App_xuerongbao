package com.wazxb.xuerongbao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.databinding.ActivityFindPasswordBinding;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.UidData;
import com.wazxb.xuerongbao.util.DeviceIDMananger;
import com.wazxb.xuerongbao.widget.InputTextView;
import com.wazxb.xuerongbao.widget.UploadImageView;
import com.zxzx74147.devlib.base.ZXBaseActivity;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;
import com.zxzx74147.devlib.utils.ZXViewHelper;

public class FindPasswordActivity extends ZXBaseActivity {
    private ActivityFindPasswordBinding mBinding = null;
    private ZXBHttpRequest mRequest = null;
    private boolean mLoginOk = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_find_password);
        mBinding.setHandler(this);

        mBinding.titleBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZXActivityJumpHelper.startActivity(FindPasswordActivity.this, RegitsterActivity.class);
            }
        });

        ZXViewHelper.dfsViewGroup(mBinding.getRoot(), new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    if (((InputTextView) view).isNotNull() && !((InputTextView) view).getIsFilled()) {
                        ((InputTextView) view).addTextChanged(mTextWatcher);
                    }
                }
            }
        });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkLoginAbled();
        }
    };

    public void checkLoginAbled() {
        mLoginOk = true;
        ZXViewHelper.dfsViewGroup(mBinding.getRoot(), new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    if (((InputTextView) view).isNotNull() && !((InputTextView) view).getIsFilled()) {
                        mLoginOk = false;
                    }
                }
            }
        });
        mBinding.confirmId.setEnabled(mLoginOk);

    }

    public void onReqeustVcode(View v) {
        if (!mBinding.phoneNumId.isReady()) {
            showToast(mBinding.phoneNumId.getError());
            return;
        }
        if (mRequest != null) {
            mRequest.cancel();
        }

        mRequest = new ZXBHttpRequest<>(UidData.class, new HttpResponseListener<UidData>() {
            @Override
            public void onResponse(HttpResponse<UidData> response) {
                if (response.error != 0) {
                    showToast(response.errorString);
                }
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_SYS_VCODE);
        mRequest.addParams(mBinding.phoneNumId.getKey(), mBinding.phoneNumId.getText());
        sendRequest(mRequest);
        mBinding.requestVcode.markSucc();
    }

    public void onConfirmClick(final View v) {
        if (mRequest != null) {
            mRequest.cancel();
        }
        String pass = mBinding.passwordId.getText();
        String pass2 = mBinding.passwordConfirmId.getText();
        if (!ZXStringUtil.checkString(pass) || pass.length() < 6) {
            showToast("请填写6位以上密码！");
            return;
        }
        if (!pass.equals(pass2)) {
            showToast("两次密码输入不一致！");
            return;
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

                        mRequest.addParams(input.getKey(), input.getText());
                    }
                } else if (view instanceof UploadImageView) {

                }
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_U_REG);
        mRequest.addParams("deviceId", DeviceIDMananger.sharedInstance().getDeviceID());
        sendRequest(mRequest);
    }
}

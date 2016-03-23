package com.wazxb.xuerongbao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityFindPasswordBinding;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.UidData;
import com.wazxb.xuerongbao.util.FillRqeustUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;

public class FindPasswordActivity extends ZXBBaseActivity {
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
        FillRqeustUtil.addWatcher(this, new FillRqeustUtil.CheckFilledListener() {
            @Override
            public void onChecked(boolean isReady) {
                mBinding.confirmId.setEnabled(isReady);
            }
        });

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
                showToast("密码重置成功！");
                mBinding.getRoot().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1000);

            }
        });
        FillRqeustUtil.fillRequest(mRequest, getWindow().getDecorView());
        mRequest.setPath(NetworkConfig.ADDRESS_PW_FIND);
        sendRequest(mRequest);
    }
}

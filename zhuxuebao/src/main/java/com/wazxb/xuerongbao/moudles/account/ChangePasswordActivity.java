package com.wazxb.xuerongbao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityChangePasswordBinding;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.UidData;
import com.wazxb.xuerongbao.util.FillRqeustUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXStringUtil;

public class ChangePasswordActivity extends ZXBBaseActivity {
    private ActivityChangePasswordBinding mBinding = null;
    private ZXBHttpRequest mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        mBinding.setHandler(this);
        FillRqeustUtil.addWatcher(this, new FillRqeustUtil.CheckFilledListener() {
            @Override
            public void onChecked(boolean isReady) {
                mBinding.change.setEnabled(isReady);
            }
        });
    }

    public void onChangeClick(View view) {
        submit();
    }


    public void submit() {
        if (!FillRqeustUtil.checkFill(this)) {
            return;
        }
        String new1 = mBinding.newPasswordId.getText();
        String new2 = mBinding.newPassword2Id.getText();
        if (!ZXStringUtil.checkString(new1) || new1.length() < 6) {
            showToast("请填写6位以上密码！");
            return;
        }
        if (!new1.equals(new2)) {
            showToast("两次密码不一致");
            return;
        }
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
                finish();
            }
        });
        FillRqeustUtil.fillRequest(mRequest, getWindow().getDecorView());
        mRequest.setPath(NetworkConfig.ADDRESS_PW_MODIFY);
        sendRequest(mRequest);
    }
}

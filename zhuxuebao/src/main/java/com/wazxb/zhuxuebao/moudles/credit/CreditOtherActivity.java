package com.wazxb.zhuxuebao.moudles.credit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityCreditOtherBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
import com.wazxb.zhuxuebao.widget.InputTextView;
import com.wazxb.zhuxuebao.widget.UploadImageView;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXStringUtil;
import com.zxzx74147.devlib.utils.ZXViewHelper;

public class CreditOtherActivity extends ZXBBaseActivity {
    ActivityCreditOtherBinding mBinding = null;
    ZXBHttpRequest<UserAllData> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit_other);
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
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().setUserAllData(response.result);
                finish();
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_CD_LIFE);
        ZXViewHelper.dfsViewGroup(getWindow().getDecorView(), new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    InputTextView input = (InputTextView) view;
                    if (ZXStringUtil.checkString(input.getKey())) {
                        mRequest.addParams(input.getKey(), input.getText());
                    }
                } else if (view instanceof UploadImageView) {
                    UploadImageView input = (UploadImageView) view;
                    if (ZXStringUtil.checkString(input.getPostKey())) {
                        String value = (String) mRequest.getParams(input.getPostKey());
                        if (ZXStringUtil.checkString(value)) {
                            if (ZXStringUtil.checkString(input.getPicKey())) {
                                value += "," + input.getPicKey();
                            }
                        } else {
                            value = input.getPicKey();
                        }
                        mRequest.addParams(input.getPostKey(), value);
                    }
                }
            }
        });
        sendRequest(mRequest);

    }


}

package com.wazxb.zhuxuebao.moudles.personal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityBindCardBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
import com.wazxb.zhuxuebao.util.FillRqeustUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 16/3/6.
 */
public class BindCradActivity extends ZXBBaseActivity {

    ActivityBindCardBinding mBinding = null;
    private ZXBHttpRequest<Object> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bind_card);
        mBinding.setHandler(this);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.user);
        }

    }

    public void onBindClick(View v) {
        if (mRequest != null) {
            mRequest.cancel();
            mRequest = null;
        }
        mRequest = new ZXBHttpRequest<>(Object.class, new HttpResponseListener<Object>() {
            @Override
            public void onResponse(HttpResponse<Object> response) {
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                finish();
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_U_BANK);
        if (!FillRqeustUtil.checkFill(this)) {
            return;
        }
        if (!FillRqeustUtil.checkFill(this)) {
            return;
        }
        FillRqeustUtil.fillRequest(mRequest, getWindow().getDecorView());
        sendRequest(mRequest);
    }


}

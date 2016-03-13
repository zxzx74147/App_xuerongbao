package com.wazxb.zhuxuebao.moudles.personal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityBindCardShowBinding;
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
public class BindCardShowActivity extends ZXBBaseActivity {

    private ActivityBindCardShowBinding mBinding = null;
    private ZXBHttpRequest<Object> mRequest = null;
    private UserAllData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bind_card_show);
        mBinding.setHandler(this);
        mData = AccountManager.sharedInstance().getUserAllData();
        if (mData != null) {
            mBinding.setData(mData.user);
        }

    }

    public void onUnBindClick(View v) {
        if (mRequest != null) {
            mRequest.cancel();
            mRequest = null;
        }
        if (mData == null) {
            return;
        }
        mRequest = new ZXBHttpRequest<>(Object.class, new HttpResponseListener<Object>() {
            @Override
            public void onResponse(HttpResponse<Object> response) {
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().requestUserAllData();
                finish();
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_U_UNBANK);
        if (!FillRqeustUtil.checkFill(this)) {
            return;
        }
        if (!FillRqeustUtil.checkFill(this)) {
            return;
        }

        mRequest.addParams("bankCard", mData.user.bankCard);

        sendRequest(mRequest);
    }


}

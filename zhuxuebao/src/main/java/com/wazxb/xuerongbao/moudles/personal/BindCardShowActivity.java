package com.wazxb.xuerongbao.moudles.personal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityBindCardShowBinding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.wazxb.xuerongbao.util.FillRqeustUtil;
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

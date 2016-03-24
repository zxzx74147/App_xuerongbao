package com.wazxb.xuerongbao.moudles.coin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityCoinAddressBinding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.GiftItemData;
import com.wazxb.xuerongbao.util.FillRqeustUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

public class CoinAddressActivity extends ZXBBaseActivity {
    ActivityCoinAddressBinding mBinding = null;
    ZXBHttpRequest<Object> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_coin_address);
        mBinding.setHandler(this);
        init();
        FillRqeustUtil.addWatcher(this, new FillRqeustUtil.CheckFilledListener() {
            @Override
            public void onChecked(boolean isReady) {
                mBinding.loginId.setEnabled(isReady);
            }
        });
    }

    public void init() {

    }

    public void onOKClick(View v) {
        submit();
    }

    public void submit() {
        if (mRequest != null) {
            mRequest.cancel();
            mRequest = null;
        }
        GiftItemData gift = (GiftItemData) getParam();
        if (gift == null) {
            return;
        }
        if (!FillRqeustUtil.checkFill(this)) {
            return;
        }
        mRequest = new ZXBHttpRequest<>(Object.class, new HttpResponseListener<Object>() {
            @Override
            public void onResponse(HttpResponse<Object> response) {
                mRequest = null;
                hideProgressBar();
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().requestUserAllData();
                finish();
            }
        });
        mRequest.addParams("itemId", gift.itemId);
        mRequest.setPath(NetworkConfig.ADDRESS_MALL_BUY);
        FillRqeustUtil.fillRequest(mRequest, getWindow().getDecorView());
        sendRequest(mRequest);
        showProgressBar();
    }

}

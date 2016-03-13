package com.wazxb.zhuxuebao.moudles.coin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityCoinAddressBinding;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.GiftItemData;
import com.wazxb.zhuxuebao.util.FillRqeustUtil;
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

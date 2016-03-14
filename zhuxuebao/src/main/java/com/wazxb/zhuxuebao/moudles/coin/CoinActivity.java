package com.wazxb.zhuxuebao.moudles.coin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityCoinBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.CoinData;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 16/3/6.
 */
public class CoinActivity extends ZXBBaseActivity {

    private ActivityCoinBinding mBinding = null;
    private ZXBHttpRequest<CoinData> mSredRequest = null;
    private ZXBHttpRequest<CoinData> mRedRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_coin);
        mBinding.setHandler(this);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.user);
        }
        refresh();

    }

    public void refresh() {
        if (mRedRequest != null) {
            mRedRequest.cancel();
            mRedRequest = null;
        }
        mRedRequest = new ZXBHttpRequest<>(CoinData.class, new HttpResponseListener<CoinData>() {
            @Override
            public void onResponse(HttpResponse<CoinData> response) {
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                CoinData data = response.result;

                mBinding.setCoindata(data);
            }
        });
        mRedRequest.setPath(NetworkConfig.ADDRESS_SYS_SIGN);
        sendRequest(mRedRequest);
    }

    public void onSignClick(View v) {
        if (mSredRequest != null) {
            mSredRequest.cancel();
            mSredRequest = null;
        }
        mSredRequest = new ZXBHttpRequest<>(CoinData.class, new HttpResponseListener<CoinData>() {
            @Override
            public void onResponse(HttpResponse<CoinData> response) {
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().requestUserAllData();
                mBinding.setCoindata(response.result);
            }
        });
        mSredRequest.setPath(NetworkConfig.ADDRESS_SYS_SSIGN);
        sendRequest(mSredRequest);
    }

    public void onFinishClick(View v) {
        finish();
    }

}

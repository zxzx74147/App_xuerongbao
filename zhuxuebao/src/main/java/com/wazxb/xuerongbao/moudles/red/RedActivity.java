package com.wazxb.xuerongbao.moudles.red;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityRedBinding;
import com.wazxb.xuerongbao.moudles.account.AccountInterface;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.RedData;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 16/3/6.
 */
public class RedActivity extends ZXBBaseActivity {

    ActivityRedBinding mBinding = null;
    private ZXBHttpRequest<RedData> mSredRequest = null;

    private ZXBHttpRequest<RedData> mRedRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_red);
        mBinding.setHandler(this);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.user);
        }

        mBinding.getRoot().setVisibility(View.INVISIBLE);
        refresh();

    }

    public void refresh() {
        showProgressBar();
        if (mRedRequest != null) {
            mRedRequest.cancel();
            mRedRequest = null;
        }
        showProgressBar();
        mRedRequest = new ZXBHttpRequest<>(RedData.class, new HttpResponseListener<RedData>() {
            @Override
            public void onResponse(HttpResponse<RedData> response) {
                hideProgressBar();
                mBinding.getRoot().setVisibility(View.VISIBLE);
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                RedData data = response.result;
                if (data.grabed == 0) {
                    mBinding.sredLayout.setVisibility(View.VISIBLE);
                    mBinding.redLayout.setVisibility(View.GONE);
                } else {
                    mBinding.sredLayout.setVisibility(View.GONE);
                    mBinding.redLayout.setVisibility(View.VISIBLE);
                    mBinding.finish.setVisibility(View.GONE);
                    mBinding.finish2.setVisibility(View.VISIBLE);
                }
                mBinding.setReddata(data);
            }
        });
        mRedRequest.setPath(NetworkConfig.ADDRESS_SYS_RED);
        sendRequest(mRedRequest);
    }

    public void onRedClick(View v) {
        if (!AccountInterface.checkLogin(this)) {
            return;
        }
        if (mSredRequest != null) {
            mSredRequest.cancel();
            mSredRequest = null;
        }
        showProgressBar();
        mSredRequest = new ZXBHttpRequest<>(RedData.class, new HttpResponseListener<RedData>() {
            @Override
            public void onResponse(HttpResponse<RedData> response) {
                hideProgressBar();
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                RedData data = response.result;
                if (data.grabed == 0) {
                    mBinding.sredLayout.setVisibility(View.VISIBLE);
                    mBinding.redLayout.setVisibility(View.GONE);
                } else {
                    mBinding.sredLayout.setVisibility(View.GONE);
                    mBinding.redLayout.setVisibility(View.VISIBLE);

                }
                AccountManager.sharedInstance().requestUserAllData();
            }
        });
        mSredRequest.setPath(NetworkConfig.ADDRESS_SYS_SRED);
        sendRequest(mSredRequest);
    }

    public void onFinishClick(View v) {
        finish();
    }

}

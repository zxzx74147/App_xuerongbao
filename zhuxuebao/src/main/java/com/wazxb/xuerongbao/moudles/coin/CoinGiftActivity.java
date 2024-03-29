package com.wazxb.xuerongbao.moudles.coin;

import android.os.Bundle;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.list.ZXBBaseListActivity;
import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ActivityCoinGiftBinding;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.GiftData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 16/3/4.
 */
public class CoinGiftActivity extends ZXBBaseListActivity {

    private ActivityCoinGiftBinding mBinding;
    private ZXBHttpRequest<GiftData> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = (ActivityCoinGiftBinding) super.mBinding;
        mBinding.setHandler(this);
        refreshData();

    }

    @Override
    protected ZXBViewBinder getItemBinder() {
        return new CoinGiftBinder();
    }

    @Override
    protected void refreshData() {
        if (mRequest != null) {
            return;
        }

        mRequest = new ZXBHttpRequest<>(GiftData.class, new HttpResponseListener<GiftData>() {
            @Override
            public void onResponse(HttpResponse<GiftData> response) {
                completeLoading();
                mRequest = null;
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                setData(response.result.itemList.item);
            }
        });
        mRequest.isRefresh = true;
        mRequest.setPath(NetworkConfig.ADDRESS_MALL_LIST);
        sendRequest(mRequest);

    }

    protected void loadMoreData() {
        if (mRequest != null) {
            return;
        }
        mRequest = new ZXBHttpRequest<>(GiftData.class, new HttpResponseListener<GiftData>() {
            @Override
            public void onResponse(HttpResponse<GiftData> response) {
                completeLoading();
                mRequest = null;
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                setData(response.result.itemList.item);
            }
        });
        mRequest.isRefresh = false;
        mRequest.setPath(NetworkConfig.ADDRESS_MALL_LIST);
        sendRequest(mRequest);

    }

    protected int getLayoutID() {
        return R.layout.activity_coin_gift;
    }
}
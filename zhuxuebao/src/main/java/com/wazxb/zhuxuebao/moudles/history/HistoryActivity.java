package com.wazxb.zhuxuebao.moudles.history;

import android.os.Bundle;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.list.ZXBBaseListActivity;
import com.wazxb.zhuxuebao.base.list.ZXBViewBinder;
import com.wazxb.zhuxuebao.databinding.ActivityHistoryBinding;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.LoanData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 16/3/11.
 */
public class HistoryActivity extends ZXBBaseListActivity {

    private ActivityHistoryBinding mBinding;
    private ZXBHttpRequest<LoanData> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = (ActivityHistoryBinding) super.mBinding;
        mBinding.setHandler(this);
        refreshData();
    }

    @Override
    protected ZXBViewBinder getItemBinder() {
        return new HistoryBinder();
    }

    @Override
    protected void refreshData() {
        if (mRequest != null) {
            return;
        }

        mRequest = new ZXBHttpRequest<>(LoanData.class, new HttpResponseListener<LoanData>() {
            @Override
            public void onResponse(HttpResponse<LoanData> response) {
                mRequest = null;
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                setData(response.result.lnList.loan);
            }
        });
        mRequest.isRefresh = true;
        mRequest.setPath(NetworkConfig.ADDRESS_LN_HISTORY);
        sendRequest(mRequest);

    }

    protected void loadMoreData() {
        if (mRequest != null) {
            return;
        }
        mRequest = new ZXBHttpRequest<>(LoanData.class, new HttpResponseListener<LoanData>() {
            @Override
            public void onResponse(HttpResponse<LoanData> response) {
                mRequest = null;
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                addData(response.result.lnList.loan);
            }
        });
        mRequest.isRefresh = true;
        mRequest.setPath(NetworkConfig.ADDRESS_LN_HISTORY);
        sendRequest(mRequest);

    }

    protected int getLayoutID() {
        return R.layout.activity_history;
    }
}

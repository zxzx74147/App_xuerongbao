package com.wazxb.xuerongbao.moudles.evaluate;

import android.os.Bundle;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.list.ZXBBaseListActivity;
import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ActivityEvaluateBinding;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.EvaluateData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 16/3/4.
 */
public class EvaluateActivity extends ZXBBaseListActivity {

    private ActivityEvaluateBinding mBinding;
    private ZXBHttpRequest<EvaluateData> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = (ActivityEvaluateBinding) super.mBinding;
        mBinding.setHandler(this);
        refreshData();
    }

    @Override
    protected ZXBViewBinder getItemBinder() {
        return new EvaluateBinder();
    }

    @Override
    protected void refreshData() {
        if (mRequest != null) {
            return;
        }

        mRequest = new ZXBHttpRequest<>(EvaluateData.class, new HttpResponseListener<EvaluateData>() {
            @Override
            public void onResponse(HttpResponse<EvaluateData> response) {
                mRequest = null;
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                setData(response.result.evaluateList.evaluate);
            }
        });
        mRequest.isRefresh = true;
        mRequest.setPath(NetworkConfig.ADDRESS_LN_EVALUATE);
        sendRequest(mRequest);

    }

    protected void loadMoreData() {
        if (mRequest != null) {
            return;
        }
        mRequest = new ZXBHttpRequest<>(EvaluateData.class, new HttpResponseListener<EvaluateData>() {
            @Override
            public void onResponse(HttpResponse<EvaluateData> response) {
                mRequest = null;
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                setData(response.result.evaluateList.evaluate);
            }
        });
        mRequest.isRefresh = false;
        mRequest.setPath(NetworkConfig.ADDRESS_LN_RETURN);
        sendRequest(mRequest);

    }

    protected int getLayoutID() {
        return R.layout.activity_evaluate;
    }
}
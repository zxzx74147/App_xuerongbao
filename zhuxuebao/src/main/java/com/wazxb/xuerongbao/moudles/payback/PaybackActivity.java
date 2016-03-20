package com.wazxb.xuerongbao.moudles.payback;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.list.ZXBBaseListActivity;
import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ActivityPaybackBinding;
import com.wazxb.xuerongbao.databinding.HeadPaybackDetailBinding;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.LoanData;
import com.wazxb.xuerongbao.storage.data.ReturnWayData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 16/3/11.
 */
public class PaybackActivity extends ZXBBaseListActivity {

    private ActivityPaybackBinding mBinding;
    private ZXBHttpRequest<LoanData> mRequest = null;
    private HeadPaybackDetailBinding mHeadBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = (ActivityPaybackBinding) super.mBinding;
        mBinding.setHandler(this);
        refreshData();
        mHeadBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.head_payback_detail, null, false);
        if (StorageManager.sharedInstance().getInitdat() != null) {
            ReturnWayData returnWayData = StorageManager.sharedInstance().getInitdat().returnWay;
            mHeadBinding.setData(returnWayData);
        }
        getRecyclerView().setNormalHeader(mHeadBinding.getRoot());

    }

    @Override
    protected ZXBViewBinder getItemBinder() {
        return new PaybackBinder();
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
        mRequest.setPath(NetworkConfig.ADDRESS_LN_RETURN);
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
        mRequest.setPath(NetworkConfig.ADDRESS_LN_RETURN);
        sendRequest(mRequest);

    }

    protected int getLayoutID() {
        return R.layout.activity_payback;
    }

    @Override
    protected int getEmptyStringID(){
        return R.string.empty_payback;
    }
}

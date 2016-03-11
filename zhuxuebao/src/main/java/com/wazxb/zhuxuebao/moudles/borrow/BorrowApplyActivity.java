package com.wazxb.zhuxuebao.moudles.borrow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityBorrowApplyBinding;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.BorrowRequestData;
import com.wazxb.zhuxuebao.util.FillRqeustUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;

/**
 * Created by zhengxin on 16/3/8.
 */
public class BorrowApplyActivity extends ZXBBaseActivity {

    private ActivityBorrowApplyBinding mBinding;
    private BorrowRequestData mRequestData;
    private ZXBHttpRequest<Object> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_borrow_apply);
        mRequestData = (BorrowRequestData) getParam();
        mBinding.setData(mRequestData);
        mBinding.setHandler(this);
    }


    public void onApplyClick(View v) {
        if (!FillRqeustUtil.checkLoginAbled(this)) {
            return;
        }
        if (mRequest != null) {
            mRequest.cancel();
            mRequest = null;
        }
        mRequest = new ZXBHttpRequest<>(Object.class, new HttpResponseListener<Object>() {
            @Override
            public void onResponse(HttpResponse<Object> response) {
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                ZXActivityJumpHelper.startActivity(BorrowApplyActivity.this, BorrowSuccActivity.class);
                finish();
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_LN_APPLY);
        FillRqeustUtil.fillRequest(mRequest, mBinding.getRoot());
        mRequest.addParams("lnProdId", mRequestData.lnProdId);
        mRequest.addParams("money", mRequestData.money);
        mRequest.addParams("time", mRequestData.day);
        mRequest.addParams("month", mRequestData.month);
        sendRequest(mRequest);
    }

}

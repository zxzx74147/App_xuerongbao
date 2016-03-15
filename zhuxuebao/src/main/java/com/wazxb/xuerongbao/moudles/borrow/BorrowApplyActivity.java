package com.wazxb.xuerongbao.moudles.borrow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityBorrowApplyBinding;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.BorrowRequestData;
import com.wazxb.xuerongbao.util.FillRqeustUtil;
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
        if (!FillRqeustUtil.checkFill(this)) {
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
        mRequest.addParams("day", mRequestData.day);
        mRequest.addParams("month", mRequestData.month);
        sendRequest(mRequest);
    }

}

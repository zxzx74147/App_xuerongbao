package com.wazxb.zhuxuebao.moudles.more;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityFeedbackBinding;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.LoanItemData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 16/3/4.
 */
public class FeedbackActivity extends ZXBBaseActivity {

    private ActivityFeedbackBinding mBinding;
    private ZXBHttpRequest<Object> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
        mBinding.setHandler(this);
    }

    public void onSubmitClick(View v) {
        if (mRequest != null) {
            mRequest.cancel();
            mRequest = null;
        }
        LoanItemData loan = (LoanItemData) getParam();
        if (loan == null) {
            return;
        }
        showProgressBar();
        mRequest = new ZXBHttpRequest<>(Object.class, new HttpResponseListener<Object>() {
            @Override
            public void onResponse(HttpResponse<Object> response) {
                hideProgressBar();
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                finish();
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_U_FEEDBACK);
        mRequest.addParams("feedback", mBinding.content.getText().toString());
        sendRequest(mRequest);
    }

}
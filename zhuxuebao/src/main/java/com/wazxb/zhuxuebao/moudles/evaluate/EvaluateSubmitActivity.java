package com.wazxb.zhuxuebao.moudles.evaluate;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityEvaluateSubmitBinding;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.LoanItemData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 16/3/4.
 */
public class EvaluateSubmitActivity extends ZXBBaseActivity {

    private ActivityEvaluateSubmitBinding mBinding;
    private ZXBHttpRequest<Object> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_evaluate_submit);
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
                if(response.hasError()){
                    showToast(response.errorString);
                    return;
                }
                finish();
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_LN_SEVALUATE);
        mRequest.addParams("lnId", loan.lnId);
        mRequest.addParams("star", (int) (mBinding.starView.getRating()));
        mRequest.addParams("hide", mBinding.evaluateAnonymity.isChecked() ? 1 : 0);
        mRequest.addParams("content", mBinding.content.getText().toString());
        sendRequest(mRequest);
    }

}
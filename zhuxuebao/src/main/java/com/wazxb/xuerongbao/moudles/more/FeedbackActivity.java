package com.wazxb.xuerongbao.moudles.more;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityFeedbackBinding;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXStringUtil;

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
        mBinding.content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ZXStringUtil.checkString(s.toString())){
                    mBinding.submit.setEnabled(true);
                }else{
                    mBinding.submit.setEnabled(false);
                }
            }
        });
    }

    public void onSubmitClick(View v) {
        if (mRequest != null) {
            mRequest.cancel();
            mRequest = null;
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
package com.wazxb.xuerongbao.moudles.common;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.list.ZXBBaseListActivity;
import com.wazxb.xuerongbao.base.list.ZXBViewBinder;
import com.wazxb.xuerongbao.databinding.ActivitySelSchoolBinding;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.SchoolSelListData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;

/**
 * Created by zhengxin on 16/3/2.
 */
public class SchoolSelActivity extends ZXBBaseListActivity {

    private ZXBHttpRequest<SchoolSelListData> mRequest;
    private ActivitySelSchoolBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = (ActivitySelSchoolBinding) super.mBinding;
        mBinding.setHandler(this);
        completeLoading();
        hideProgressBar();
        mBinding.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mRequest != null) {
                    mRequest.cancel();
                    mRequest = null;
                }
                mRequest = new ZXBHttpRequest<>(SchoolSelListData.class, new HttpResponseListener<SchoolSelListData>() {
                    @Override
                    public void onResponse(HttpResponse<SchoolSelListData> response) {
                        mRequest = null;
                        if (response.hasError()) {
                            showToast(response.error);
                            return;
                        }
                        if (response.result != null && response.result.schoolList != null) {
                            setData(response.result.schoolList.schoolName);
                            hideEmptyView();
                        }
                    }
                });

                mRequest.addParams("school", s.toString().trim());
                mRequest.setPath(NetworkConfig.ADDRESS_SE_SCHOOL);
                sendRequest(mRequest);
            }
        });

    }

    @Override
    protected ZXBViewBinder getItemBinder() {
        return new SchoolSelBinder();
    }

    @Override
    protected void refreshData() {

    }

    protected int getLayoutID() {
        return R.layout.activity_sel_school;
    }
}

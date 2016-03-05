package com.wazxb.zhuxuebao.moudles.account;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.sdk.android.util.Md5Utils;
import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.ActivityRegitster4Binding;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.UidData;
import com.wazxb.zhuxuebao.util.DeviceIDMananger;
import com.wazxb.zhuxuebao.widget.InputTextView;
import com.wazxb.zhuxuebao.widget.UploadImageView;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXStringUtil;
import com.zxzx74147.devlib.utils.ZXViewHelper;
import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/2/20.
 */
public class RegisterFragment4 extends BaseFragment {

    private ZXBHttpRequest mRequest = null;
    private ActivityRegitster4Binding mBinding = null;
    private Boolean mLoginOk = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_regitster_4, container, false);
        mBinding.setFragment(this);
        if (mActivity != null) {
            mBinding.setHandler((RegitsterActivity) mActivity);
        }
        ZXViewHelper.dfsViewGroup(mBinding.getRoot(), new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    if (((InputTextView) view).isNotNull() && !((InputTextView) view).getIsFilled()) {
                        ((InputTextView) view).addTextChanged(mTextWatcher);
                    }
                }
            }
        });
        return mBinding.getRoot();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (mBinding != null) {
            mBinding.setHandler((RegitsterActivity) context);
        }
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkLoginAbled();
        }
    };


    public void checkLoginAbled() {
        mLoginOk = true;
        ZXViewHelper.dfsViewGroup(mBinding.getRoot(), new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    if (((InputTextView) view).isNotNull() && !((InputTextView) view).getIsFilled()) {
                        mLoginOk = false;
                    }
                }
            }
        });
        mBinding.loginId.setEnabled(mLoginOk);

    }

    public void onReqeustVcode(View v) {
        if (!mBinding.phoneNumId.isReady()) {
            showToast(mBinding.phoneNumId.getError());
            return;
        }
        if (mRequest != null) {
            mRequest.cancel();
        }

        mRequest = new ZXBHttpRequest<>(UidData.class, new HttpResponseListener<UidData>() {
            @Override
            public void onResponse(HttpResponse<UidData> response) {
                if (response.error != 0) {
                    showToast(response.errorString);
                }
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_SYS_VCODE);
        mRequest.addParams(mBinding.phoneNumId.getKey(), mBinding.phoneNumId.getText());
        mActivity.sendRequest(mRequest);
        mBinding.requestVcode.markSucc();
    }

    public void onRegistClick(final View v) {
        if (mRequest != null) {
            mRequest.cancel();
        }
        String pass = mBinding.passwordId.getText();
        String pass2 = mBinding.passwordConfirmId.getText();
        if (!ZXStringUtil.checkString(pass) || pass.length() < 6) {
            showToast("请填写6位以上密码！");
            return;
        }
        if (!pass.equals(pass2)) {
            showToast("两次密码输入不一致！");
            return;
        }
        mRequest = new ZXBHttpRequest<>(UidData.class, new HttpResponseListener<UidData>() {
            @Override
            public void onResponse(HttpResponse<UidData> response) {
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().saveUid(response.result.uId);
                mActivity.finish();
            }
        });
        ZXViewHelper.dfsViewGroup(mActivity.getWindow().getDecorView(), new ZXViewHelper.IViewProcess() {
            @Override
            public void processView(View view) {
                if (view instanceof InputTextView) {
                    InputTextView input = (InputTextView) view;
                    if (ZXStringUtil.checkString(input.getKey())) {
                        if (input != mBinding.passwordId) {
                            mRequest.addParams(input.getKey(), input.getText());
                        } else {
                            mRequest.addParams(input.getKey(), Md5Utils.md5Digest(input.getText().getBytes()));
                        }
                    }
                } else if (view instanceof UploadImageView) {

                }
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_U_REG);
        mRequest.addParams("deviceId", DeviceIDMananger.sharedInstance().getDeviceID());
        mActivity.sendRequest(mRequest);
    }
}

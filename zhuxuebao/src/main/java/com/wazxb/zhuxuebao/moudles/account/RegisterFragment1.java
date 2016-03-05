package com.wazxb.zhuxuebao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.ActivityRegitster1Binding;
import com.wazxb.zhuxuebao.widget.InputTextView;
import com.zxzx74147.devlib.utils.ZXStringUtil;
import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/2/20.
 */
public class RegisterFragment1 extends BaseFragment {

    ActivityRegitster1Binding mBinding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_regitster_1, container, false);
        return mBinding.getRoot();
    }


    public static String dfsViewGroup(View view) {
        if (view == null) {
            return "";
        }
        if (view instanceof InputTextView) {
            if (!((InputTextView) view).isReady()) {
                return ((InputTextView) view).getError();
            }
        }
        if (view instanceof ViewGroup) {
            int childNum = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childNum; i++) {
                String error = dfsViewGroup(((ViewGroup) view).getChildAt(i));
                if (ZXStringUtil.checkString(error)) {
                    return error;
                }
            }
        }
        return "";
    }


    public boolean checkDone() {
        String error = dfsViewGroup(mBinding.getRoot());
        if (ZXStringUtil.checkString(error)) {
            showToast(error);
            return false;
        }
        return true;

    }

}

package com.wazxb.zhuxuebao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.ActivityRegitster3Binding;
import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/2/20.
 */
public class RegisterFragment3 extends BaseFragment {

    ActivityRegitster3Binding mBinding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_regitster_3, container, false);
        return mBinding.getRoot();
    }
}

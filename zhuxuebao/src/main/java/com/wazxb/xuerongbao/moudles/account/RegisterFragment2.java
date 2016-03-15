package com.wazxb.xuerongbao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.databinding.ActivityRegitster2Binding;
import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/2/20.
 */
public class RegisterFragment2 extends BaseFragment {

    ActivityRegitster2Binding mBinding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_regitster_2, container, false);
        return mBinding.getRoot();
    }
}

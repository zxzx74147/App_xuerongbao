package com.wazxb.zhuxuebao.moudles.credit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.ActivityCreditBase3Binding;
import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/2/20.
 */
public class CreditBaseFragment3 extends BaseFragment {

    ActivityCreditBase3Binding mBinding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_credit_base_3, container, false);
        return mBinding.getRoot();
    }


    public boolean checkDone() {

        return true;

    }

}

package com.wazxb.zhuxuebao.moudles.evaluate;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityRegitster2Binding;

/**
 * Created by zhengxin on 16/3/4.
 */
public class EvaluateActivity extends ZXBBaseActivity {
    private ActivityRegitster2Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_evaluate);


    }
}
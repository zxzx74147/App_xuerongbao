package com.wazxb.xuerongbao.moudles.borrow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityBorrowSuccBinding;

/**
 * Created by zhengxin on 16/3/8.
 */
public class BorrowSuccActivity extends ZXBBaseActivity {

    private ActivityBorrowSuccBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_borrow_succ);
    }


}

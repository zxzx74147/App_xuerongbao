package com.wazxb.xuerongbao.moudles.payback;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityPaybackDetailBinding;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.LoanData;
import com.wazxb.xuerongbao.storage.data.LoanItemData;
import com.wazxb.xuerongbao.storage.data.ReturnWayData;

/**
 * Created by zhengxin on 16/3/11.
 */
public class PaybackDetailActivity extends ZXBBaseActivity {

    private ActivityPaybackDetailBinding mBinding;
    private ZXBHttpRequest<LoanData> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_payback_detail);
        mBinding.setHandler(this);
        LoanItemData data = (LoanItemData) getParam();
        mBinding.setLoanData(data);
        if (StorageManager.sharedInstance().getInitdat() != null) {
            ReturnWayData returnWayData = StorageManager.sharedInstance().getInitdat().returnWay;
            mBinding.setData(returnWayData);
        }
    }

}

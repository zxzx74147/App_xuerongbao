package com.wazxb.zhuxuebao.moudles.payback;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityPaybackDetailBinding;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.StorageManager;
import com.wazxb.zhuxuebao.storage.data.LoanData;
import com.wazxb.zhuxuebao.storage.data.LoanItemData;
import com.wazxb.zhuxuebao.storage.data.ReturnWayData;

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

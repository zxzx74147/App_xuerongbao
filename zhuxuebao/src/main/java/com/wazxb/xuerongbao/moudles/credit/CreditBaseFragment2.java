package com.wazxb.xuerongbao.moudles.credit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.databinding.ActivityCreditBase2Binding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.InitData;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/2/20.
 */
public class CreditBaseFragment2 extends BaseFragment {

    ActivityCreditBase2Binding mBinding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_credit_base_2, container, false);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.cdBase);
        }
        InitData init = StorageManager.sharedInstance().getInitdat();
        if (init != null) {
            mBinding.setContract(init.contract);
        }
        return mBinding.getRoot();
    }


    public boolean checkDone() {
//        String error = CheckUtil.checkDone(mBinding.getRoot());
//        if (ZXStringUtil.checkString(error)) {
//            showToast(error);
//            return false;
//        }
        return true;

    }

}

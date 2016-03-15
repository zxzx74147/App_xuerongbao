package com.wazxb.xuerongbao.moudles.calculate;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.databinding.FragmentCaculateBinding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.moudles.borrow.BorrowConfig;
import com.wazxb.xuerongbao.storage.data.CalculatorData;
import com.wazxb.xuerongbao.storage.data.ProdData;
import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/3/8.
 */
public class CaculateFragment extends BaseFragment {

    private int mMode = BorrowConfig.BORROW_FULI;

    FragmentCaculateBinding mBinding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_caculate, container, true);
        mBinding.setHandler(this);
        fillData();
        return mBinding.getRoot();
    }

    public void setMode(int mode) {
        mMode = mode;

    }


    private void fillData() {
        CalculatorData data = AccountManager.sharedInstance().getCalData();
        if (data == null || data.lnProdList == null || data.lnProdList.prod == null) {
            AccountManager.sharedInstance().requestCaculateData();
            return;
        }
        for (ProdData prod : data.lnProdList.prod) {
            if (mMode == prod.lnProdId) {
                mBinding.setData(prod);
                break;
            }
        }
    }

}
package com.wazxb.zhuxuebao.moudles.borrow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.FragmentCommonBorrowBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.storage.data.BorrowRequestData;
import com.wazxb.zhuxuebao.storage.data.CalculatorData;
import com.wazxb.zhuxuebao.storage.data.ProdData;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/3/8.
 */
public class CommonBorrowFragment extends BaseFragment {

    private int mMode = BorrowConfig.BORROW_FULI;
    private FragmentCommonBorrowBinding mBinding = null;
    private BorrowRequestData mRequestData = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_common_borrow, container, true);
        mBinding.setHandler(this);
        mRequestData = new BorrowRequestData();
        fillData();
        return mBinding.getRoot();
    }

    public void setMode(int mode) {
        mMode = mode;

    }

    public void onBorrowClick(View v) {

        mBinding.cashView.fillRequestData(mRequestData);
        ZXActivityJumpHelper.startActivity(getActivity(), BorrowApplyActivity.class, mRequestData);
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
                mRequestData.mProdData = prod;
                break;
            }
        }
    }

}
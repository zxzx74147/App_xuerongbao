package com.wazxb.xuerongbao.moudles.borrow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.databinding.FragmentCommonBorrowBinding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.moudles.personal.BindCradActivity;
import com.wazxb.xuerongbao.storage.data.BorrowRequestData;
import com.wazxb.xuerongbao.storage.data.CalculatorData;
import com.wazxb.xuerongbao.storage.data.ProdData;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXDialogUtil;
import com.zxzx74147.devlib.utils.ZXStringUtil;
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
        UserAllData user = AccountManager.sharedInstance().getUserAllData();
        if (user == null || user.user == null || !ZXStringUtil.checkString(user.user.bank)) {
            ZXDialogUtil.showCheckDialog(getActivity(), R.string.bind_bank_remind, new Runnable() {
                @Override
                public void run() {
                    ZXActivityJumpHelper.startActivity(getActivity(), BindCradActivity.class);
                }
            });
            return;
        }
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
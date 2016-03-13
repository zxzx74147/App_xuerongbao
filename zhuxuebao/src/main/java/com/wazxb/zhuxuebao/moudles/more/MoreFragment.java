package com.wazxb.zhuxuebao.moudles.more;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.FragmentMoreBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountInterface;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/2/20.
 */
public class MoreFragment extends BaseFragment {
    private FragmentMoreBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, true);
        mBinding.setHandler(this);
        return mBinding.getRoot();
    }

    public void onAboutClick(View v) {
        ZXActivityJumpHelper.startActivity(this, AboutActivity.class);
    }

    public void onFeedBackClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        ZXActivityJumpHelper.startActivity(this, FeedbackActivity.class);
    }

    public void onContractClick(View v) {
        ZXActivityJumpHelper.startActivity(this, ContractActivity.class);
    }


    public void onUpdateClick(View v) {

    }

    public void onClearCacheClick(View v) {

    }

    public void onGreenHandClick(View v) {

    }

    public void onHelpClick(View v) {

    }
}

package com.wazxb.zhuxuebao.moudles.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.FragmentHomeBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountInterface;
import com.wazxb.zhuxuebao.moudles.borrow.BorrowActivity;
import com.wazxb.zhuxuebao.moudles.calculate.CaculateActivity;
import com.wazxb.zhuxuebao.moudles.credit.CreditActivity;
import com.wazxb.zhuxuebao.moudles.payback.PaybackActivity;
import com.wazxb.zhuxuebao.storage.StorageManager;
import com.wazxb.zhuxuebao.storage.data.InitData;
import com.wazxb.zhuxuebao.widget.BannerView;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/2/20.
 */
public class HomeFragment extends BaseFragment {
    FragmentHomeBinding mBinding = null;
    private BannerView mBannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, true);
        mBinding.creditCeilingValue.setNumber(500);
        mBinding.setHandler(this);
        mBannerView = new BannerView(getContext());
        mBinding.bannerLayout.addView(mBannerView.getRootView(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initData();
        return mBinding.getRoot();
    }

    private void initData() {
        if (StorageManager.sharedInstance().getInitdat().ad != null) {
            mBannerView.setData(StorageManager.sharedInstance().getInitdat().ad.carousel);
        }
        StorageManager.sharedInstance().requestInitData(new HttpResponseListener<InitData>() {
            @Override
            public void onResponse(HttpResponse<InitData> response) {
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                mBannerView.setData(response.result.ad.carousel);

            }
        });
    }

    public void onBorrowClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        ZXActivityJumpHelper.startActivity(this, BorrowActivity.class);
    }

    public void onRepaymentClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        ZXActivityJumpHelper.startActivity(this, PaybackActivity.class);
    }

    public void onInterestCalClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        ZXActivityJumpHelper.startActivity(this, CaculateActivity.class);
    }

    public void onEvaluateClick(View v) {

    }

    public void onCreditClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        ZXActivityJumpHelper.startActivity(this, CreditActivity.class);
    }

}

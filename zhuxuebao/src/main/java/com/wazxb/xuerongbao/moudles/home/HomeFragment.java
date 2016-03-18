package com.wazxb.xuerongbao.moudles.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wazxb.xuerongbao.EventBusConfig;
import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.databinding.FragmentHomeBinding;
import com.wazxb.xuerongbao.moudles.account.AccountInterface;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.moudles.borrow.BorrowActivity;
import com.wazxb.xuerongbao.moudles.calculate.CaculateActivity;
import com.wazxb.xuerongbao.moudles.credit.CreditActivity;
import com.wazxb.xuerongbao.moudles.evaluate.EvaluateActivity;
import com.wazxb.xuerongbao.moudles.message.MessageListActivity;
import com.wazxb.xuerongbao.moudles.message.MessageManager;
import com.wazxb.xuerongbao.moudles.payback.PaybackActivity;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.InitData;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.wazxb.xuerongbao.widget.BannerView;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.widget.BaseFragment;

import de.greenrobot.event.EventBus;

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
        mBinding.titleBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.titleBar.setRightText(R.drawable.index_no_msg);
                if (!AccountInterface.checkLogin(getActivity())) {
                    return;
                }
                ZXActivityJumpHelper.startActivity(getActivity(), MessageListActivity.class);
            }
        });
        EventBus.getDefault().register(this);
        return mBinding.getRoot();
    }

    private void initData() {

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
        UserAllData user = AccountManager.sharedInstance().getUserAllData();
        if (user != null) {
            mBinding.creditCeilingValue.setNumber((int) user.user.quotaTotal);
        }
        if (StorageManager.sharedInstance().getInitdat().ad != null) {
            mBannerView.setData(StorageManager.sharedInstance().getInitdat().ad.carousel);
            return;
        }
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
        ZXActivityJumpHelper.startActivity(this, EvaluateActivity.class);
    }

    public void onCreditClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        ZXActivityJumpHelper.startActivity(this, CreditActivity.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus

    }

    @Override
    public void onStop() {
        super.onStop();
        //取消EventBus
    }

    @Override
    public void onResume(){
        super.onResume();
        if (MessageManager.sharedInstance().getNewNum() > 0) {
            mBinding.titleBar.setRightDrawable(R.drawable.index_has_msg);
        }else{
            mBinding.titleBar.setRightDrawable(R.drawable.index_no_msg);
        }
    }

    //事件1接收者：在主线程接收
    public void onEvent(String event) {
        if (EventBusConfig.EVENT_FRESH_USER_DATA.equals(event)) {
            UserAllData user = AccountManager.sharedInstance().getUserAllData();
            if (user != null) {
                user.user.quotaTotal = user.user.quotaTotal > 5000 ? 5000 : user.user.quotaTotal;
                mBinding.creditCeilingValue.setNumber((int) user.user.quotaTotal);
            }
        } else if (EventBusConfig.EVENT_MESSAGE_REFRESH.equals(event)) {
            if (MessageManager.sharedInstance().getNewNum() > 0) {
                mBinding.titleBar.setRightDrawable(R.drawable.index_has_msg);
            }else{
                mBinding.titleBar.setRightDrawable(R.drawable.index_no_msg);
            }
        }
    }

}

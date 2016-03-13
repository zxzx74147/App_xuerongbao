package com.wazxb.zhuxuebao.moudles.personal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.zhuxuebao.EventBusConfig;
import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.FragmentPersonalBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountInterface;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.moudles.coin.CoinGiftActivity;
import com.wazxb.zhuxuebao.moudles.message.MessageListActivity;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;
import com.zxzx74147.devlib.widget.BaseFragment;

import de.greenrobot.event.EventBus;

/**
 * Created by zhengxin on 16/2/20.
 */
public class PersonalFragment extends BaseFragment {
    private FragmentPersonalBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal, container, true);
        mBinding.setHandler(this);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.user);
        } else {
            mBinding.setData(null);
        }
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //取消EventBus
        EventBus.getDefault().unregister(this);
    }

    //事件1接收者：在主线程接收
    public void onEvent(String event) {
        Log.e("event", event);
        if (EventBusConfig.EVENT_FRESH_USER_DATA.equals(event)) {
            UserAllData data = AccountManager.sharedInstance().getUserAllData();
            if (data != null) {
                mBinding.setData(data.user);
            }
        }
    }

    public void onPersonalInfoClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        ZXActivityJumpHelper.startActivity(this, PersonalInfoActivity.class);
    }

    public void onBindBankClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        UserAllData user = AccountManager.sharedInstance().getUserAllData();
        if (user != null && user.user != null && ZXStringUtil.checkString(user.user.bank)) {
            ZXActivityJumpHelper.startActivity(this, BindCardShowActivity.class, user.user);
        } else {
            ZXActivityJumpHelper.startActivity(this, BindCradActivity.class);
        }
    }

    public void onCoinGiftClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        ZXActivityJumpHelper.startActivity(this, CoinGiftActivity.class);
    }

    public void onMessageClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        ZXActivityJumpHelper.startActivity(this, MessageListActivity.class);
    }

    public void onHistoryClick(View v) {
        if (!AccountInterface.checkLogin(getActivity())) {
            return;
        }
        ZXActivityJumpHelper.startActivity(this, MessageListActivity.class);
    }


}

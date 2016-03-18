package com.wazxb.xuerongbao.moudles.personal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wazxb.xuerongbao.EventBusConfig;
import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.common.share.CommonShareDialog;
import com.wazxb.xuerongbao.databinding.FragmentPersonalBinding;
import com.wazxb.xuerongbao.moudles.account.AccountInterface;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.moudles.coin.CoinGiftActivity;
import com.wazxb.xuerongbao.moudles.history.HistoryActivity;
import com.wazxb.xuerongbao.moudles.message.MessageListActivity;
import com.wazxb.xuerongbao.moudles.message.MessageManager;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.InitData;
import com.wazxb.xuerongbao.storage.data.UserAllData;
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
        EventBus.getDefault().register(this);
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus

    }

    @Override
    public void onResume() {
        super.onResume();
        int num = MessageManager.sharedInstance().getNewNum();
        if (num == 0) {
            mBinding.myMessageId.setRemind(null);
        } else {
            mBinding.myMessageId.setRemind(String.valueOf(MessageManager.sharedInstance().getNewNum()));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //事件1接收者：在主线程接收
    public void onEvent(String event) {
        if (EventBusConfig.EVENT_FRESH_USER_DATA.equals(event)) {
            UserAllData data = AccountManager.sharedInstance().getUserAllData();
            if (data == null) {
                mBinding.setData(null);
            } else {
                mBinding.setData(data.user);
            }
            if (data == null) {
                mBinding.head.setImageResource(R.drawable.user_head);

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
        ZXActivityJumpHelper.startActivity(this, HistoryActivity.class);
    }

    public void onShareClick(View v) {
        InitData data = StorageManager.sharedInstance().getInitdat();
        CommonShareDialog dialog = new CommonShareDialog(getActivity());
        dialog.setShareData(data.share);
    }


}

package com.wazxb.xuerongbao.moudles.more;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wazxb.xuerongbao.EventBusConfig;
import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.common.webview.CommonWebActivity;
import com.wazxb.xuerongbao.databinding.FragmentMoreBinding;
import com.wazxb.xuerongbao.moudles.update.UpdateDialog;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.InitData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.AsyncHelper;
import com.zxzx74147.devlib.utils.SharedPreferenceHelper;
import com.zxzx74147.devlib.utils.TimerUtil;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXFileUtil;
import com.zxzx74147.devlib.widget.BaseFragment;

import java.io.File;

import de.greenrobot.event.EventBus;

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

    @Override
    public void onShow(boolean showTip) {
        super.onShow(showTip);
        AsyncHelper.executeAsyncTask(new AsyncHelper.BDTask<Integer>() {
            @Override
            public Integer executeBackGround() {
                File file = Glide.getPhotoCacheDir(getContext());
                return ZXFileUtil.getFileSize(file);
            }

            @Override
            public void postExecute(Integer result) {
                String text = String.format("%.2fM", result / 1024f / 1024);
                mBinding.clearCacheId.setContent(text);
            }
        });
    }

    public void onAboutClick(View v) {
        ZXActivityJumpHelper.startActivity(this, AboutActivity.class);
    }

    public void onFeedBackClick(View v) {

        ZXActivityJumpHelper.startActivity(this, FeedbackActivity.class);
    }

    public void onContractClick(View v) {
        ZXActivityJumpHelper.startActivity(this, ContractActivity.class);
    }


    public void onUpdateClick(View v) {
        StorageManager.sharedInstance().requestInitData(new HttpResponseListener<InitData>() {
            @Override
            public void onResponse(HttpResponse<InitData> response) {
                InitData data = StorageManager.sharedInstance().getInitdat();
                if (data.upgrade != null && data.upgrade.show != 0) {
                    ZXActivityJumpHelper.startActivity(getActivity(), UpdateDialog.class, data.upgrade);
                } else {
                    showToast("已经是最新版本！");
                }
            }
        });
    }

    public void onClearCacheClick(View v) {
        ((ZXBBaseActivity) mActivity).showProgressBar();
        AsyncHelper.executeAsyncTask(new AsyncHelper.BDTask<Integer>() {
            @Override
            public Integer executeBackGround() {
                Glide.get(getContext()).clearDiskCache();
                return null;
            }

            @Override
            public void postExecute(Integer result) {
                ((ZXBBaseActivity) mActivity).hideProgressBar();
                mBinding.clearCacheId.setContent("0.0M");
                showToast("清理完成");
            }
        });


    }

    public void onGreenHandClick(View v) {
        InitData initData = StorageManager.sharedInstance().getInitdat();
        if (initData != null && initData.contract != null) {
            CommonWebActivity.startActivity(getActivity(), null, initData.contract.guide);
        }
    }

    public void onHelpClick(View v) {
        InitData initData = StorageManager.sharedInstance().getInitdat();
        if (initData != null && initData.contract != null) {
            CommonWebActivity.startActivity(getActivity(), null, initData.contract.help);
        }
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
        if (EventBusConfig.EVENT_INIT_DONE.equals(event)) {
            InitData data = StorageManager.sharedInstance().getInitdat();
            if (data.upgrade != null && data.upgrade.show != 0) {
                long mLastShowTime = SharedPreferenceHelper.getLong("last_show_update", 0);
                long now = System.currentTimeMillis();
                if (now - mLastShowTime < TimerUtil.DAY) {
                    return;
                }
                SharedPreferenceHelper.saveLong("last_show_update", now);
                ZXActivityJumpHelper.startActivity(getActivity(), UpdateDialog.class, data.upgrade);
            }
        }
    }
}

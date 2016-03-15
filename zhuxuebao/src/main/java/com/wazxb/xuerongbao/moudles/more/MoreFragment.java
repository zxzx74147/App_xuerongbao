package com.wazxb.xuerongbao.moudles.more;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.common.webview.CommonWebActivity;
import com.wazxb.xuerongbao.databinding.FragmentMoreBinding;
import com.wazxb.xuerongbao.moudles.account.AccountInterface;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.InitData;
import com.zxzx74147.devlib.utils.AsyncHelper;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXFileUtil;
import com.zxzx74147.devlib.widget.BaseFragment;

import java.io.File;

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
                mBinding.clearCacheId.setContent(result / 1024f / 1024 + "M");
            }
        });
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
        File file = Glide.getPhotoCacheDir(getContext());
        ZXFileUtil.deleteFile(file);
        mBinding.clearCacheId.setContent("0M");
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
}

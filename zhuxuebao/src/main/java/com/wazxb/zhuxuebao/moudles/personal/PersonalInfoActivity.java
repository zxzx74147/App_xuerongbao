package com.wazxb.zhuxuebao.moudles.personal;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.common.portrait.EditPortraitActivity;
import com.wazxb.zhuxuebao.databinding.ActivityPersonalInfoBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.moudles.gesturepass.GestureActivity;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
import com.wazxb.zhuxuebao.util.ImageUtil;
import com.wazxb.zhuxuebao.util.RequestCode;
import com.wazxb.zhuxuebao.util.ZXUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;

/**
 * Created by zhengxin on 16/3/6.
 */
public class PersonalInfoActivity extends ZXBBaseActivity {


    private ActivityPersonalInfoBinding mBinding = null;
    private ZXBHttpRequest<Object> mRequest = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info);
        mBinding.setHandler(this);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.user);
        }
    }

    public void onLogoutClick(View v) {
        AccountManager.sharedInstance().logout();
        finish();
    }

    public void onChangePortraitClick(View v) {
        ZXUtil.takePhoto(this, RequestCode.REQUEST_MSG_PHOTO);
    }

    public void onGesturePassClick(View v) {
        ZXActivityJumpHelper.startActivity(this, GestureActivity.class);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCode.REQUEST_MSG_PHOTO:
                    if (data == null || data.getData() == null) {
                        return;
                    }
                    Uri uri = data.getData();
                    if (uri != null) {
                        EditPortraitActivity.startActivityForResult(this, uri, RequestCode.REQUEST_PROFILE_EDIT_PORTRAIT, EditPortraitActivity.MODE_UPLOAD);
                    } else {
                        showToast("图片载入失败");
                    }
                    break;
                case RequestCode.REQUEST_PROFILE_EDIT_PORTRAIT:
                    if (data == null) {
                        return;
                    }
                    String key = data.getStringExtra("key");
                    String url = data.getStringExtra("url");
                    ImageUtil.loadImage(url, mBinding.head);
                    submit(key);
                    break;
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case RequestCode.REQUEST_PROFILE_EDIT_PORTRAIT:
                    ZXUtil.takePhoto(this, RequestCode.REQUEST_MSG_PHOTO);
            }
        }
    }

    private void submit(String key) {
        showProgressBar();
        mRequest = new ZXBHttpRequest<>(Object.class, new HttpResponseListener<Object>() {
            @Override
            public void onResponse(HttpResponse<Object> response) {
                hideProgressBar();
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().requestUserAllData();
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_U_UPPORTRAIT);
        mRequest.addParams("picKey", key);
        sendRequest(mRequest);
    }
}

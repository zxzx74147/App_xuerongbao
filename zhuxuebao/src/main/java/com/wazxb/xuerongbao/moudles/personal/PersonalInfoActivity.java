package com.wazxb.xuerongbao.moudles.personal;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.common.portrait.EditPortraitActivity;
import com.wazxb.xuerongbao.databinding.ActivityPersonalInfoBinding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.moudles.account.ChangePasswordActivity;
import com.wazxb.xuerongbao.moudles.gesturepass.GestureActivity;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.wazxb.xuerongbao.util.ImageUtil;
import com.wazxb.xuerongbao.util.RequestCode;
import com.wazxb.xuerongbao.util.ZXUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXDialogUtil;

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
        ZXDialogUtil.showCheckDialog(this, R.string.logout_remind, new Runnable() {
            @Override
            public void run() {
                AccountManager.sharedInstance().logout();
                finish();
            }
        });

    }

    public void onChangePortraitClick(View v) {
        ZXUtil.takePhoto(this, RequestCode.REQUEST_MSG_PHOTO);
    }

    public void onGesturePassClick(View v) {
        ZXActivityJumpHelper.startActivity(this, GestureActivity.class);
    }

    public void onChangePassClick(View v) {
        ZXActivityJumpHelper.startActivity(this, ChangePasswordActivity.class);
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

    @Override
    public void onResume() {
        super.onResume();
        if(AccountManager.sharedInstance().getUid() == null){
            finish();
        }
    }
}

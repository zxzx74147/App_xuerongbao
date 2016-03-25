package com.wazxb.xuerongbao.moudles.personal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.zxzx74147.devlib.utils.ZXFileUtil;
import com.zxzx74147.devlib.utils.ZXStringUtil;

import java.io.File;

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

    String mPicPath = null;

    public void onChangePortraitClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("上传头像");
        String[] items = new String[]{"拍照", "照片库"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                if (item == 1) {
                    ZXUtil.takePhoto(PersonalInfoActivity.this, RequestCode.REQUEST_MSG_PHOTO);
                } else {
                    mPicPath = System.currentTimeMillis() + ".jpg";
                    ZXUtil.showFrontCamera(PersonalInfoActivity.this, RequestCode.REQUEST_MSG_PHOTO, mPicPath);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

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

                    Uri fileUri = null;
                    if (data == null) {
                        data = new Intent();
                    }
                    if (data.getData() != null) {
                        fileUri = data.getData();
                    } else if (ZXStringUtil.checkString(mPicPath)) {
                        File file = ZXFileUtil.getFile(mPicPath);
                        if (file.exists()) {
                            fileUri = Uri.fromFile(file);
                        }
                        mPicPath = null;
                    }
                    if (fileUri != null) {
                        EditPortraitActivity.startActivityForResult(this, fileUri, RequestCode.REQUEST_PROFILE_EDIT_PORTRAIT, EditPortraitActivity.MODE_UPLOAD);
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
        if (AccountManager.sharedInstance().getUid() == null) {
            finish();
        }
    }
}

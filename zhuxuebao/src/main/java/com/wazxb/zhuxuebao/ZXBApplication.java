package com.wazxb.zhuxuebao;

import android.app.Application;
import android.util.Log;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.storage.StorageManager;
import com.wazxb.zhuxuebao.storage.data.InitData;
import com.zxzx74147.devlib.ZXApplicationDelegate;

/**
 * Created by zhengxin on 16/2/20.
 */
public class ZXBApplication extends Application {
    private static final String TAG = "ZXBApplication";
    private static InitData mInitData = new InitData();

    public void onCreate() {
        super.onCreate();
        ZXApplicationDelegate.onCreate(this);

        AlibabaSDK.asyncInit(this, new InitResultCallback() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "init onesdk success");
            }

            @Override
            public void onFailure(int code, String message) {
                Log.e(TAG, "init onesdk failed : " + message);
            }
        });
        StorageManager.sharedInstance();
        AccountManager.sharedInstance().requestUserAllData();

    }


}

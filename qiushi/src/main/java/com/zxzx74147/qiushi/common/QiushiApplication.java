package com.zxzx74147.qiushi.common;

import android.app.Application;

import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.qiushi.libs.network.QSRequestManageer;

/**
 * Created by zhengxin on 15/9/1.
 */
public class QiushiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZXApplicationDelegate.onCreate(this);
        QSRequestManageer.sharedInstance();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}

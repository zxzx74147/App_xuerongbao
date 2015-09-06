package com.zxzx74147.devlib.network;

import android.app.Application;

/**
 * Created by zhengxin on 15/9/1.
 */
public class NetworkInterface {

    public static void init(Application application){
        HttpManager.init(application);
    }
}

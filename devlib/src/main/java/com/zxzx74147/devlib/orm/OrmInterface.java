package com.zxzx74147.devlib.orm;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by zhengxin on 15/9/1.
 */
public class OrmInterface {

    public static void init(Application application){
        ActiveAndroid.initialize(application);
    }
}

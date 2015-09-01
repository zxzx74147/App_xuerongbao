package com.zxzx74147.devlib;

import android.app.Application;

import com.zxzx74147.devlib.image.ImageModelInterface;
import com.zxzx74147.devlib.orm.OrmInterface;

/**
 * Created by zhengxin on 15/8/26.
 */
public class ZXApplicationDelegate {

    public static Application mApplication;

    public static Application getApplication(){
        return mApplication;
    }

    public static void onCreate(Application application) {
        mApplication = application;
        ImageModelInterface.init(mApplication);
        OrmInterface.init(mApplication);
    }

}

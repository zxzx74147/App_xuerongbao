package com.zxzx74147.qiushi.libs.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.zxzx74147.devlib.ZXApplicationDelegate;

/**
 * Created by zhengxin on 15/9/16.
 */
public class DeviceUtil {

    private static String mAndroidID = null;

    public static String getAndroidId(){
        if (mAndroidID != null) {
            return mAndroidID;
        }
        TelephonyManager localObject = (TelephonyManager) ZXApplicationDelegate.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        String id  = "\"DEVICEID\":\"" + localObject.getDeviceId() + "\"-" + "\"ANDROID_ID\":\"" + Settings.Secure.getString(ZXApplicationDelegate.getApplication().getContentResolver(), "android_id") + "\"";
        mAndroidID = "IMEI_" + MD5.md5(id);
        return mAndroidID;
    }
}

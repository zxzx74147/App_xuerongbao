package com.wazxb.zhuxuebao.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.utils.SharedPreferenceHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * DeviceID用于区分设备，要保证绝对唯一，生成之后内外均存储。生成规则待定 ; TODO
 * 对于内外存均需要存储数据，统一提供操作类，并向上层隐藏同步过程 ;
 *
 * @author zhengxin
 */
public class DeviceIDMananger {
    private static DeviceIDMananger mMananger;
    private static String FILE_NAME = "device_id.bak";

    private static final String KEY_DEVICE_ID = "key_device_id";

    private String mDeviceID = null;
    private String mPhoneNum = null;

    private DeviceIDMananger() {
        // 初始化mDeviceID
        mDeviceID = getDeviceIDFromInnerStorage();
        mPhoneNum = generatePhoneNum();
        if (!ZXStringUtil.checkString(mDeviceID)) {
            mDeviceID = generateDeviceID();
            saveDeviceIDTOInnerStorage(mDeviceID);
        }
    }

    public static DeviceIDMananger sharedInstance() {
        if (mMananger == null) {
            mMananger = new DeviceIDMananger();
        }
        return mMananger;
    }

    public String getDeviceID() {
        return mDeviceID;
    }

    public String getPhoneNum() {
        return mPhoneNum;
    }

    // 生成DeviceID
    private String generateDeviceID() {
        // TODO
        Context context = ZXApplicationDelegate.getApplication().getApplicationContext();
        String androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEIStr = tm.getDeviceId();
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macStr = info.getMacAddress();
        String DevID = IMEIStr + macStr + androidId;
        return DevID;
    }

    private String generatePhoneNum() {
        try {
            TelephonyManager telephonyManager;
            telephonyManager = (TelephonyManager) ZXApplicationDelegate.getApplication()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getLine1Number();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    // 从内部存储读取DeviceID
    private String getDeviceIDFromInnerStorage() {
        return SharedPreferenceHelper.getString(KEY_DEVICE_ID, null);


    }

    // 存储DeviceID到内
    private void saveDeviceIDTOInnerStorage(String deviceId) {
        SharedPreferenceHelper.saveString(KEY_DEVICE_ID, deviceId);
    }

}

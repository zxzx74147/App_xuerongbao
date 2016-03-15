package com.wazxb.xuerongbao.storage;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.lusfold.androidkeyvaluestore.KVStore;
import com.wazxb.xuerongbao.EventBusConfig;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.InitData;
import com.wazxb.xuerongbao.util.DeviceIDMananger;
import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.AsyncHelper;
import com.zxzx74147.devlib.utils.ZXJsonUtil;
import com.zxzx74147.devlib.utils.ZXStringUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by zhengxin on 16/3/4.
 */
public class StorageManager {
    private static final String KEY_INIT_DATA = "key_init_data";

    private static StorageManager mInstance;
    private InitData mInitData = new InitData();

    private StorageManager() {
        AsyncHelper.executeAsyncTask(new AsyncHelper.BDTask() {
            @Override
            public Object executeBackGround() {
                String initData = KVStore.getInstance().get(KEY_INIT_DATA);
                if (ZXStringUtil.checkString(initData)) {
                    mInitData = ZXJsonUtil.fromJsonString(initData, InitData.class);
                }
                return null;
            }

            @Override
            public void postExecute(Object result) {

            }
        });
    }

    public static StorageManager sharedInstance() {
        if (mInstance == null) {
            mInstance = new StorageManager();
        }
        return mInstance;
    }

    public String getKVStringSync(String key) {
        String value = KVStore.getInstance().get(key);
        return value;
    }


    public void saveKVObjectAsync(final String key, final Object value) {
        AsyncHelper.executeAsyncTask(new AsyncHelper.BDTask() {
            @Override
            public Object executeBackGround() {
                String valueStr = ZXJsonUtil.toJsonString(value);
                KVStore.getInstance().insertOrUpdate(key, valueStr);
                return null;
            }

            @Override
            public void postExecute(Object result) {

            }
        });
    }

    public void saveKVStringAsync(final String key, final String value) {
        AsyncHelper.executeAsyncTask(new AsyncHelper.BDTask() {
            @Override
            public Object executeBackGround() {
                KVStore.getInstance().insertOrUpdate(key, value);
                return null;
            }

            @Override
            public void postExecute(Object result) {

            }
        });
    }

    public InitData getInitdat() {
        return mInitData;
    }

    // 获取版本信息
    public static String getVersionInfo() {
        PackageManager packageManager = ZXApplicationDelegate.getApplication()
                .getPackageManager();
        PackageInfo packInfo = null;
        ApplicationInfo appInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(ZXApplicationDelegate.getApplication().getPackageName(), 0);
            appInfo = packageManager.getApplicationInfo(packInfo.packageName,
                    PackageManager.GET_META_DATA);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void requestInitData(final HttpResponseListener<InitData> listener) {
        ZXBHttpRequest<InitData> request = new ZXBHttpRequest<>(InitData.class, new HttpResponseListener<InitData>() {
            @Override
            public void onResponse(HttpResponse<InitData> response) {
                if (response.hasError()) {
                    return;
                }
                mInitData = response.result;
                saveKVObjectAsync(KEY_INIT_DATA, mInitData);
                EventBus.getDefault().post(EventBusConfig.EVENT_INIT_DONE);
                listener.onResponse(response);
            }
        });
        request.addParams("uid", AccountManager.sharedInstance().getUid());
        request.addParams("deviceId", DeviceIDMananger.sharedInstance().getDeviceID());
        request.addParams("deviceOs", "Android");
        request.addParams("deviceType", Build.MODEL);
        request.addParams("deviceOp", Build.VERSION.RELEASE);
        request.addParams("version", getVersionInfo());
        request.setPath(NetworkConfig.ADDRESS_SYS_INIT);
        request.send();
    }


}

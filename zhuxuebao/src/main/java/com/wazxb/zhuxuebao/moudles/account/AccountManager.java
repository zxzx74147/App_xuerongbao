package com.wazxb.zhuxuebao.moudles.account;

import com.wazxb.zhuxuebao.storage.StorageManager;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
import com.zxzx74147.devlib.utils.SharedPreferenceHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/3/1.
 */
public class AccountManager {

    private static String SP_KEY_UID = "uid";
    private static String SP_KEY_USER_ALL_DATA = "user_all_data";
    private static AccountManager mInstance = null;

    private String mUid = null;
    private UserAllData mUserAllData = null;

    private AccountManager() {
        mUid = SharedPreferenceHelper.getString(SP_KEY_UID, null);
    }

    public static AccountManager sharedInstance() {
        if (mInstance == null) {
            mInstance = new AccountManager();
        }
        return mInstance;
    }

    public void saveUid(String uid) {
        mUid = uid;
        SharedPreferenceHelper.saveString(SP_KEY_UID, uid);
    }

    public void clearUid() {
        mUid = null;
        SharedPreferenceHelper.saveString(SP_KEY_UID, null);
    }

    public String getUid() {
        return mUid;
    }

    public boolean hasUid() {
        if (ZXStringUtil.checkString(mUid)) {
            return true;
        }
        return false;
    }

    public void setUserAllData(UserAllData data) {
        mUserAllData = data;
        StorageManager.sharedInstance().saveKVObjectAsync(SP_KEY_USER_ALL_DATA, mUserAllData);
    }
}

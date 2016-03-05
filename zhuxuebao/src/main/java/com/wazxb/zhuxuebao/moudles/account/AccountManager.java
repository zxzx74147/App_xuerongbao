package com.wazxb.zhuxuebao.moudles.account;

import com.zxzx74147.devlib.utils.SharedPreferenceHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/3/1.
 */
public class AccountManager {

    private static String SP_KEY_UID = "uid";
    private static AccountManager mInstance = null;

    private String mUid = null;

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

    public String getUid() {
        return mUid;
    }

    public boolean hasUid() {
        if (ZXStringUtil.checkString(mUid)) {
            return true;
        }
        return false;
    }
}

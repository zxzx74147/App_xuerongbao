package com.wazxb.xuerongbao.moudles.account;

import com.wazxb.xuerongbao.EventBusConfig;
import com.wazxb.xuerongbao.moudles.gesturepass.GesturePassManager;
import com.wazxb.xuerongbao.moudles.message.MessageManager;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.CalculatorData;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.SharedPreferenceHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by zhengxin on 16/3/1.
 */
public class AccountManager {

    private static String SP_KEY_UID = "uid";
    private static String SP_KEY_USER_ALL_DATA = "user_all_data";
    private static String SP_KEY_CAL_DATA = "user_cal_data";
    private static String SP_KEY_USER_PASS_WORD = "user_pass_word";
    private static AccountManager mInstance = null;
    private ZXBHttpRequest<UserAllData> mRequest = null;
    private ZXBHttpRequest<CalculatorData> mCalRequest = null;
    private ZXBHttpRequest<CalculatorData> mProdRequest = null;
    private String mUid = null;
    private UserAllData mUserAllData = null;
    private String mPass = null;
    private CalculatorData mCaculatorData = null;
    private CalculatorData mProdData = null;

    private AccountManager() {
        mUid = SharedPreferenceHelper.getString(SP_KEY_UID, null);
        mPass = SharedPreferenceHelper.getString(SP_KEY_USER_PASS_WORD, null);
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
        requestUserAllData();
        requestCaculateData();
    }

    public void clearAll() {
        clearUid();
        mCaculatorData = null;
        mUserAllData = null;
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
        EventBus.getDefault().post(EventBusConfig.EVENT_FRESH_USER_DATA);
        if (mUserAllData != null && mUserAllData.user != null) {
            if (mPass == null || !mPass.equals(mUserAllData.user.gesture)) {
                setPassword(mUserAllData.user.gesture);
                GesturePassManager.sharedInstance().checkPass(null);
            }
        }
    }

    public void setCalData(CalculatorData data) {
        mCaculatorData = data;
        StorageManager.sharedInstance().saveKVObjectAsync(SP_KEY_CAL_DATA, mCaculatorData);
        EventBus.getDefault().post(EventBusConfig.EVENT_CAL_DATA_READY);
    }

    public void logout() {
        saveUid(null);
        setPassword(null);
        mUserAllData = null;
        MessageManager.sharedInstance().clear();
        EventBus.getDefault().post(EventBusConfig.EVENT_FRESH_USER_DATA);
    }

    public UserAllData getUserAllData() {
        return mUserAllData;
    }

    public CalculatorData getCalData() {
        return mCaculatorData;
    }

    public CalculatorData getProdData() {
        return mProdData;
    }

    public void setPassword(String pass) {
        mPass = pass;
        if (mUserAllData != null && mUserAllData.user != null) {
            mUserAllData.user.gesture = pass;
        }
        SharedPreferenceHelper.saveString(SP_KEY_USER_PASS_WORD, pass);
    }

    public String getPassword() {
        return mPass;
    }

    public void requestUserAllData() {
        if (mRequest != null) {
            return;
        }
        if (mUid == null) {
            return;
        }
        mRequest = new ZXBHttpRequest<>(UserAllData.class, new HttpResponseListener<UserAllData>() {
            @Override
            public void onResponse(HttpResponse<UserAllData> response) {
                mRequest = null;
                if (response.hasError()) {
                    return;
                }
                mUserAllData = response.result;
                setUserAllData(mUserAllData);
                EventBus.getDefault().post(EventBusConfig.EVENT_FRESH_USER_DATA);
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_CD_INFO);
        mRequest.addParams("uId", mUid);
        mRequest.send();

    }

    public void requestCaculateData() {
        if (mCalRequest != null) {
            mCalRequest.cancel();
            mCalRequest = null;
            return;
        }
        mCalRequest = new ZXBHttpRequest<>(CalculatorData.class, new HttpResponseListener<CalculatorData>() {
            @Override
            public void onResponse(HttpResponse<CalculatorData> response) {
                mCalRequest = null;
                if (response.hasError()) {
                    return;
                }
                setCalData(response.result);
            }
        });
        mCalRequest.setPath(NetworkConfig.ADDRESS_LN_CALCULATOR);
        mCalRequest.send();
    }

    public void requestProdData() {
        if (mProdRequest != null) {
            mProdRequest.cancel();
            mProdRequest = null;
            return;
        }

        mProdRequest = new ZXBHttpRequest<>(CalculatorData.class, new HttpResponseListener<CalculatorData>() {
            @Override
            public void onResponse(HttpResponse<CalculatorData> response) {
                mProdRequest = null;
                if (response.hasError()) {
                    return;
                }
                mProdData = response.result;
            }
        });
        mProdRequest.setPath(NetworkConfig.ADDRESS_LN_PROD);
        mProdRequest.send();
    }

}

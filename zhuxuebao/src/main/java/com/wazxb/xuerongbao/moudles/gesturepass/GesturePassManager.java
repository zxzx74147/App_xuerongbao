package com.wazxb.xuerongbao.moudles.gesturepass;

import android.app.Activity;

import com.wazxb.xuerongbao.EventBusConfig;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.util.ActivityStateManager;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/3/14.
 */
public class GesturePassManager {
    private static GesturePassManager mInstance = null;

    private GesturePassManager() {
        de.greenrobot.event.EventBus.getDefault().register(this);
    }

    public static GesturePassManager sharedInstance() {
        if (mInstance == null) {
            mInstance = new GesturePassManager();
        }
        return mInstance;
    }

    public void onEvent(String event) {
        if (EventBusConfig.EVENT_FRONT_BACK_CHANGED.equals(event)) {
            checkPass();
        }
    }

    public void checkPass(){
        String mPass = AccountManager.sharedInstance().getPassword();
        if (!ZXStringUtil.checkString(mPass)) {
            return;
        }
        boolean isBackGround = ActivityStateManager.sharedInstance().getIsBackGround();
        Activity activity = ActivityStateManager.sharedInstance().getTopActivity();
        if (!isBackGround && activity != null) {
            ZXActivityJumpHelper.startActivity(activity, GesturePasswordActivity.class, GesturePasswordActivity.MODE_CHECK);
        }
    }
}
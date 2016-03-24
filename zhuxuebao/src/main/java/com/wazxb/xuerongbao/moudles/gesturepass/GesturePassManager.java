package com.wazxb.xuerongbao.moudles.gesturepass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
    private long lastCheckTime = System.currentTimeMillis();

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
            checkPass(null);
        }
    }

    public void checkPass(Context context) {
        String mPass = AccountManager.sharedInstance().getPassword();
        if (!ZXStringUtil.checkString(mPass)) {
            return;
        }
        if(System.currentTimeMillis()-lastCheckTime<3000){
            return;
        }
        boolean isBackGround = ActivityStateManager.sharedInstance().getIsBackGround();
        Activity activity = ActivityStateManager.sharedInstance().getTopActivity();
        if (!isBackGround && (context != null || activity != null)) {
            lastCheckTime = System.currentTimeMillis();
            Intent intent = null;
            if (activity != null) {
                intent = new Intent(activity, GesturePasswordActivity.class);
            } else {
                intent = new Intent(context, GesturePasswordActivity.class);
            }
            intent.putExtra(ZXActivityJumpHelper.INTENT_DATA, GesturePasswordActivity.MODE_CHECK);
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            activity.startActivity(intent);
//            ZXActivityJumpHelper.startActivity(activity, GesturePasswordActivity.class, GesturePasswordActivity.MODE_CHECK);
        }
    }
}

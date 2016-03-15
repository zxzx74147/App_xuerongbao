package com.wazxb.xuerongbao.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.wazxb.xuerongbao.EventBusConfig;
import com.zxzx74147.devlib.ZXApplicationDelegate;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 用于管理前后台
 *
 * @author zhengxin
 */
public class ActivityStateManager {
    private static ActivityStateManager mManager;
    private Activity mTopActivity;

    private boolean isScreenOn = true;

    private static volatile boolean isBackground = true;// 记录是否后台，内部使用，锁屏为true；

    private static String mActivityName;

    private ActivityStateManager() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
//        TbadkApplication.getInst().registerReceiver(new ScreenBroadcastReceiver(), filter);
    }

    public static ActivityStateManager sharedInstance() {
        if (mManager == null) {
            mManager = new ActivityStateManager();
        }
        return mManager;
    }


    public void onResume(Activity activity) {
        mTopActivity = activity;
        mActivityName = activity.getClass().getName();
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                boolean temp = isApplicationBackground();
                if (isBackground != temp) {
                    isBackground = temp;
                    sendActivityStateMessage(isBackground);
                }
            }
        });

    }

    public void onPause(Activity activity) {
        if (mTopActivity == activity) {
            mTopActivity = null;
        }
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                boolean temp = isApplicationBackground();
                if (isBackground != temp) {
                    isBackground = temp;
                    sendActivityStateMessage(isBackground);
                }
            }
        });
        mActivityName = activity.getClass().getName();
    }

    public void onScreenOn() {
        isScreenOn = true;
        boolean temp = isApplicationBackground();
        if (isBackground != temp) {
            isBackground = temp;
            sendActivityStateMessage(isBackground);
        }
    }

    public void onScreenOff() {
        isScreenOn = false;
        if (isBackground) {
            return;
        }
        isBackground = true;
        sendActivityStateMessage(isBackground);
    }

    public boolean getIsBackGround() {
        return isApplicationBackground();
    }

    public boolean getIsScreenOn() {
        return isScreenOn;
    }


    private static void sendActivityStateMessage(boolean isBackground) {
        EventBus.getDefault().post(EventBusConfig.EVENT_FRONT_BACK_CHANGED);
    }

    public static boolean isApplicationBackground() {// 程序是否后台，锁屏时返回锁屏钱状态
        try {
            ActivityManager am = (ActivityManager) ZXApplicationDelegate.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                if (!topActivity.getPackageName().equals(ZXApplicationDelegate.getApplication().getPackageName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    public Activity getTopActivity() {
        return mTopActivity;
    }


}

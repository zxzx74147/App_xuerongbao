package com.zxzx74147.devlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zhengxin on 15/8/31.
 */
public class ZXActivityJumpHelper {
    private ZXActivityJumpHelper(){

    }

    public static void startActivity(Context context ,Class<? extends Activity> T){
        Intent intent = new Intent(context,T);
        context.startActivity(intent);
    }

}

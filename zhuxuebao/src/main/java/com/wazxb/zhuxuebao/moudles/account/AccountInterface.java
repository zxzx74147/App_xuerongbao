package com.wazxb.zhuxuebao.moudles.account;

import android.content.Context;

import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;

/**
 * Created by zhengxin on 16/2/20.
 */
public class AccountInterface {

    public static boolean checkLogin(Context context) {
        ZXActivityJumpHelper.startActivity(context, LoginActivity.class);
        return true;
    }
}

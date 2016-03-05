package com.wazxb.zhuxuebao.util;

/**
 * Created by zhengxin on 16/2/27.
 */
public class IDUtil {
    private static int mStart = 1;

    public static synchronized int genID() {
        mStart++;
        return mStart;
    }
}

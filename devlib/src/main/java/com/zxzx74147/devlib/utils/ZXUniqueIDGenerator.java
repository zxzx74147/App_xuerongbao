package com.zxzx74147.devlib.utils;

/**
 * Created by zhengxin on 15/9/15.
 */
public class ZXUniqueIDGenerator {
    private static int mStartID = 1000;

    public static synchronized int getUniqueID(){
        return mStartID++;
    }
}

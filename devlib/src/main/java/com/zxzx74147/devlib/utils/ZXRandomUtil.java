package com.zxzx74147.devlib.utils;

import java.util.Random;

/**
 * Created by zhengxin on 15/9/15.
 */
public class ZXRandomUtil {

    private static Random mRandom = new Random(System.currentTimeMillis());

    public static int getRandomInt(int max) {
        return getRandomInt(0, max);
    }

    public static int getRandomInt(int start, int end) {
        if (end < start) {
            return -1;
        }
        int inv = end - start;
        int random = mRandom.nextInt(inv);
        return start + random;
    }

    public static byte[] getRandomBytes(int len) {
        byte[] buf = new byte[len];
        mRandom.nextBytes(buf);
        return buf;
    }

    public static double getRandomDouble(double max) {
        return mRandom.nextDouble() * max;
    }
}

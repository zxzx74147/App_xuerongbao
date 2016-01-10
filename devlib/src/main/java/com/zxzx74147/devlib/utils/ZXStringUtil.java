package com.zxzx74147.devlib.utils;

/**
 * Created by zhengxin on 16/1/10.
 */
public class ZXStringUtil {

    public static boolean checkString(String src) {
        if (src == null) {
            return false;
        }
        if (src.length() == 0) {
            return false;
        }
        return true;
    }
}

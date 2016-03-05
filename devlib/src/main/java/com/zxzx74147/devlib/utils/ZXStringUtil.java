package com.zxzx74147.devlib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean matchRegex(String src, String regex) {
        if (!ZXStringUtil.checkString(regex)) {
            return true;
        }
        if (src == null) {
            return false;
        }
        try {
            Pattern r = Pattern.compile(regex);
            Matcher m = r.matcher(src);
            if (m.find()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String formatPhone(String phone) {
        if(!checkString(phone)){
            return phone;
        }
        phone = phone.replace("-","");
        phone = phone.replace(" ","");
        return phone;
    }
}

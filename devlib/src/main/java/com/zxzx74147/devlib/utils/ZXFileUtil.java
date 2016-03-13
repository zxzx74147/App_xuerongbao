package com.zxzx74147.devlib.utils;

import android.os.Environment;

import com.zxzx74147.devlib.ZXApplicationDelegate;

import java.io.File;

/**
 * Created by zhengxin on 15/8/27.
 */
public class ZXFileUtil {
    private static boolean HAS_SDCARD = false;
    private static String SD_PATH;
    private static String INNER_PATH;
    private static String DIVIDER;

    static {
        HAS_SDCARD = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        SD_PATH = Environment.getExternalStorageDirectory().getPath();
        INNER_PATH = ZXApplicationDelegate.getApplication().getFilesDir().getPath();
        DIVIDER = File.pathSeparator;
    }

    private static String splicePath(String str1, String str2) {
        if (str1.endsWith(DIVIDER)) {
            str1 = str1.substring(0, str1.length() - 1);
        }
        if (str2.startsWith(DIVIDER)) {
            str2 = str1.substring(0, str2.length() - 1);
        }
        String path = str1 + DIVIDER + str2;
        return path;
    }

    public static boolean hasSDCard() {
        return HAS_SDCARD;
    }

    public File getInnerPathFile(String path) {
        return null;
    }

    public static int getFileSize(File file) {
        int result = 0;
        if (file == null) {
            return result;
        }
        if (file.listFiles() == null) {
            return result;
        }
        for (File item : file.listFiles()) {
            result += getFileSize(item);
        }
        return result;
    }

    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (file.isDirectory() && file.listFiles() != null) {
            for (File item : file.listFiles()) {
                deleteFile(item);
            }
        }
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }


}

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

    private static String splicePath(String str1,String str2){
        String path = str1;
        if (path.endsWith(DIVIDER)){
            //TODO
        }
        return path;
    }

    public static boolean hasSDCard(){
        return HAS_SDCARD;
    }

    public File getInnerPathFile(String path){
        return null;
    }


}

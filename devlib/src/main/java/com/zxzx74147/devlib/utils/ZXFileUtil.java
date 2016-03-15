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
    public static final File EXTERNAL_STORAGE_DIRECTORY = Environment.getExternalStorageDirectory();
    private static String APP_DIR = "xrb";

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

    public static String getFilePath(String appath, String filename) {
        String file = null;
        if (filename != null && filename.startsWith("/")) {
            filename = filename.substring(1, filename.length());
        }
        if (appath != null && appath.startsWith("/")) {
            appath = appath.substring(1, appath.length());
        }
        if (appath != null && appath.length() > 0) {
            file = EXTERNAL_STORAGE_DIRECTORY + "/" + APP_DIR + "/" + appath + "/" + filename;
        } else {
            file = EXTERNAL_STORAGE_DIRECTORY + "/" + APP_DIR + "/" + filename;
        }
        return file;
    }


    public static File getFile(String fileName) {
        try {
            String file = getFilePath(null, fileName);
            File fileObj = new File(file);
            return fileObj;
        } catch (SecurityException ex) {
            BdLog.e("FileHelper", "GetFile", "error = " + ex.getMessage());
            return null;
        }
    }

    /**
     * 获得文件夹中的文件对象（不区分文件是否存在）
     *
     * @param filename 文件名
     * @return 成功：File； 失败：空
     */
    public static File getFile(String appath, String filename) {


        try {
            String file = getFilePath(appath, filename);
            File fileObj = new File(file);
            return fileObj;
        } catch (SecurityException ex) {
            BdLog.e("FileHelper", "GetFile", "error = " + ex.getMessage());
            return null;
        }
    }

    public static File createFileIfNotFound(String appath, String filename) {
        try {
            File file = getFile(appath, filename);
            if (file.exists()) {
                return file;
            } else {
                if (file.createNewFile() == true) {
                    return file;
                } else {
                    return null;
                }
            }
        } catch (Exception ex) {
            BdLog.e("FileHelper", "CreateFile", "error = " + ex.getMessage());
            return null;
        }
    }

    public static File createFileIfNotFound(String file) {
        return createFileIfNotFound(null, file);
    }
}

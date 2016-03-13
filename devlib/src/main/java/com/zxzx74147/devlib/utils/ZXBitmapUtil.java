package com.zxzx74147.devlib.utils;

import android.graphics.Bitmap;

/**
 * Created by zhengxin on 16/3/13.
 */
public class ZXBitmapUtil {
    public static Bitmap resize(Bitmap bitmap, int size) {
        if (bitmap == null) {
            return null;
        }
        if (size < 1) {
            return bitmap;
        }
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int m = Math.min(height, width);
        if (m < size) {
            return bitmap;
        }
        float scale = (float) size / m;
        int dstHeight = (int) (height * scale);
        int dstWidth = (int) (width * scale);
        Bitmap bm = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
        return bm;
    }
}

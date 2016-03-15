package com.wazxb.xuerongbao.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by zhengxin on 16/2/27.
 */
public class ZXUtil {

    public static void takePhoto(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }


    public static void takePhoto(android.support.v4.app.Fragment fragment, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(intent, requestCode);
    }
}

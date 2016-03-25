package com.wazxb.xuerongbao.util;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;

import com.zxzx74147.devlib.utils.ZXFileUtil;

import java.io.File;

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

    public static void showFrontCamera(Activity activity, int requestCode, String path) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2); // 调用前置摄像头
        intent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_FRONT);
        intent.putExtra("autofocus", true); // 自动对焦
        File file = ZXFileUtil.getFile(path);
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, requestCode);
    }
}

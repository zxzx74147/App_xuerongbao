package com.zxzx74147.devlib.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.RectF;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zhengxin on 16/1/10.
 */
public class ZXDialogUtil {
    public static Dialog showDialog(Context context, View rootView, RectF contentRect) {
        Dialog mDialog = new AlertDialog.Builder(context).create();
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();

        if (window == null) {
            return null;
        }

        mDialog.show();
        // 添加动画
//        window.setWindowAnimations(R.style.share_dialog_center_style);
        // 设置为底部对齐
        window.setGravity(Gravity.CENTER);
        // 适应屏幕宽度
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // 视图填充
        // 设置监听，包括成功、失败、取消时候的动作
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.width = (int) (ZXViewHelper.getScreenW() * 0.9);
        // lp.x = (int) (BdUtilHelper.getEquipmentWidth(mContext) * 0.15);
        window.setAttributes(lp);
        window.setContentView(rootView);
        return mDialog;
    }

}

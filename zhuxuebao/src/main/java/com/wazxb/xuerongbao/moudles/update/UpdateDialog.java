package com.wazxb.xuerongbao.moudles.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.storage.data.UpgradeData;

public class UpdateDialog extends ZXBBaseActivity {


    private UpgradeData mData = null;
    private Dialog mDialog = null;

    /**
     * 供外部启动UpdateDialog
     *
     * @param context
     * @param data
     */
    public static void startActivity(Context context, UpgradeData data) {
        if (data == null) {
            return;
        }

        Intent intent = new Intent(context, UpdateDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(UpdateConfig.TAG_DATA, data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 解决魅族手机对话框背景为暗色问题
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.1f;
        getWindow().setAttributes(lp);
        initView();

        InitData(savedInstanceState);

    }

    private void initView() {
    }

    /**
     * 初始化对话框，显示相应的数据 ；注册对话框的监听接口
     *
     * @param savedInstanceState
     */
    private void InitData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mData = (UpgradeData) savedInstanceState.getSerializable(UpdateConfig.TAG_DATA);
        } else {
            Intent intent = getIntent();
            if (intent != null) {
                mData = (UpgradeData) intent.getSerializableExtra(UpdateConfig.TAG_DATA);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDialog.this);
        builder.setMessage(mData.msg);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(UpdateDialog.this, UpdateService.class);
                intent.putExtra(UpdateConfig.TAG_DATA, mData);
                startService(intent);
                UpdateDialog.this.finish();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mData.force != 0) {
                    moveTaskToBack(true);
                } else {
                    dialog.dismiss();
                }

            }
        });
        mDialog = builder.create();
        mDialog.show();
        if (mDialog != null) {
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface arg0) {
                    UpdateDialog.this.finish();
                }

            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mData != null) {
            outState.putSerializable(UpdateConfig.TAG_DATA, mData);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


}
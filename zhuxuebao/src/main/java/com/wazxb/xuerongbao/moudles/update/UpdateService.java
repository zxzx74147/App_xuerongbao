package com.wazxb.xuerongbao.moudles.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.network.http.BdHttpManager2;
import com.wazxb.xuerongbao.network.http.HttpContext2;
import com.wazxb.xuerongbao.network.http.HttpRequest2;
import com.wazxb.xuerongbao.storage.data.UpgradeData;
import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.utils.AsyncHelper;
import com.zxzx74147.devlib.utils.BdLog;
import com.zxzx74147.devlib.utils.CustomToast;
import com.zxzx74147.devlib.utils.ZXFileUtil;

import java.io.File;


public class UpdateService extends Service {

    private NotificationManager mNotificationManager = null;
    private Notification mNotify = null;
    private AsyncTask mTask = null;
    private UpgradeData mData = null;


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(final Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == UpdateConfig.NET_MSG_GETLENTH) {

                if (mNotify != null && msg.arg2 > 0) {
                    int progress = (int) (msg.arg1 * 100L / msg.arg2);
                    mNotify.contentView.setProgressBar(R.id.progress, 100, progress, false);
                    StringBuffer buffer = new StringBuffer(20);
                    buffer.append(String.valueOf(msg.arg1 / 1000));
                    buffer.append("K/");
                    buffer.append(String.valueOf(msg.arg2 / 1000));
                    buffer.append("K");
                    mNotify.contentView.setTextViewText(R.id.schedule, buffer);
                    mNotificationManager.notify(UpdateConfig.NOTIFY_DOWNLOADING_ID, mNotify);
                }
            } else if (msg.what == 1) {
                UpgradeData data = (UpgradeData) msg.obj;
                if (data != null) {
                    // BdUtilHelper.install_apk(BBApplication.getInst(),
                    // UpdateConfig.FILE_NAME);
                }
                stopSelf();
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotify = getUpdateNotification();
        if (mNotificationManager == null) {
            stopSelf();
        }
    }

    public Notification getUpdateNotification() {
        PendingIntent intent = PendingIntent.getActivity(ZXApplicationDelegate.getApplication(), 0, new Intent(), 0);
        Notification notification = new Notification(R.drawable.logo, null, System.currentTimeMillis());
        notification.contentView = new RemoteViews(ZXApplicationDelegate.getApplication().getPackageName(), R.layout.service_notify_item_view);
        notification.contentView.setProgressBar(R.id.progress, 100, 0, false);
        notification.contentIntent = intent;
        notification.icon = R.drawable.logo;
        notification.flags = Notification.FLAG_NO_CLEAR; // 点击了通知栏中的"清除通知"后，此通知不清除
        return notification;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        handler.removeMessages(UpdateConfig.NET_MSG_GETLENTH);
        // if (mModel != null) {
        // mModel.cancelLoadData();
        // }
        if (mTask != null) {
            mTask.cancel(true);
        }
        if (mNotificationManager != null) {
            mNotificationManager.cancel(UpdateConfig.NOTIFY_DOWNLOADING_ID);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        if (intent == null) {
            return result;
        }
        mData = (UpgradeData) intent.getSerializableExtra(UpdateConfig.TAG_DATA);
        if (mData == null) {
            return result;
        }

        String info = getString(R.string.downloading);
        mNotify.contentView.setTextViewText(R.id.info, info);
        mNotify.contentView.setTextViewText(R.id.schedule, "0/0");

        File file = ZXFileUtil.getFile(UpdateConfig.FILE_NAME);
        if (file != null && file.exists()) {
            file.delete();
        }

        if (mTask == null) {
            mTask = startDownload();
            mNotify.contentView.setProgressBar(R.id.progress, 100, 0, false);
            mNotificationManager.notify(UpdateConfig.NOTIFY_DOWNLOADING_ID, mNotify);
        }
        return result;
    }

    private HttpContext2 context;

    private AsyncTask startDownload() {
        return AsyncHelper.executeAsyncTask(new AsyncHelper.BDTask<Boolean>() {
            @Override
            public Boolean executeBackGround() {
                context = new HttpContext2();
                Boolean ret = false;
                try {
                    context.getRequest().setUrl(mData.url);
                    context.getRequest().setMethod(HttpRequest2.HTTP_METHOD.GET);

                    BdHttpManager2 httpCore2 = new BdHttpManager2(context);
                    ret = httpCore2.downloadFile(UpdateConfig.FILE_NAME, handler, UpdateConfig.NET_MSG_GETLENTH, 3, 300 * 1000, 10 * 1000);

                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    BdLog.e(this.getClass().getName(), "doInBackground", ex.getMessage());
                }
                return ret;
            }

            @Override
            public void postExecute(Boolean result) {
                mNotificationManager.cancel(UpdateConfig.NOTIFY_DOWNLOADING_ID);
                mTask = null;
                if (result == false) {
                    CustomToast.newInstance().showToast("下载失败");
                } else {
                    installCustomApk(ZXApplicationDelegate.getApplication(),
                            UpdateConfig.FILE_NAME);
                }
                stopSelf();
            }
        });
    }


    public void installCustomApk(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(ZXFileUtil.getFile(UpdateConfig.FILE_NAME)),
                "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}

package com.wazxb.xuerongbao.common.share;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.storage.data.ShareData;
import com.wazxb.xuerongbao.util.ImageUtil;
import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.utils.BdLog;
import com.zxzx74147.devlib.utils.CustomToast;

import java.util.ArrayList;

/**
 * 分享
 * Created by zhengxin on 15/6/1.
 */
public class NewShareProxy {
    private String mFrom;
    private Activity mActivity;
    public static boolean WX_FRIEND_SUPPORT = true;

    private IWXAPI mWXApi;
    private Tencent mTencent = null;
    private IWeiboShareAPI mWeiboShareAPI = null;
    private CustomToast mToast = null;

    private static final String IMG_URL = "http://www.kkxlclub.com/forum.php?mod=viewthread&tid=648&from=timeline&isappinstalled=0";

    public NewShareProxy(Activity activity) {
        mActivity = activity;
        init();
    }

    public void init() {
        mToast = CustomToast.newInstance();
        regWX();
        regQQ();
        regWeiBo();

    }


    private void regWX() {
        mWXApi = WXAPIFactory.createWXAPI(ZXApplicationDelegate.getApplication(), ShareConfig.WXAPPID);
        boolean result = mWXApi.registerApp(ShareConfig.WXAPPID);
        if (!result) {
            BdLog.e("WX REGIST ERROR");
            return;
        }
        int version = mWXApi.getWXAppSupportAPI();
        if (version < 0x21020001) {
            WX_FRIEND_SUPPORT = false;
        } else {
            WX_FRIEND_SUPPORT = true;
        }
    }

    private void regQQ() {
        mTencent = Tencent.createInstance(ShareConfig.QQAPPID, mActivity);
    }

    private void regWeiBo() {
        try {
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity, ShareConfig.WBAPPID);
            if (!mWeiboShareAPI.isWeiboAppInstalled()) {
                return;
            }
            boolean result = mWeiboShareAPI.registerApp(); // 将应用注册到微博客户端
            if (!result) {
                BdLog.e("WB REGIST ERROR");
            }
        } catch (Exception e) {

        }
    }

    public boolean shareToWeibo(ShareData data) {
        if (!mWeiboShareAPI.isWeiboAppInstalled()) {


            mToast.showToast("微博未安装");
            return false;
        }
        if (!mWeiboShareAPI.isWeiboAppSupportAPI()) {
            mToast.showToast("当前微博版本不支持分享");
            return false;
        }
        WeiboMultiMessage mMessage = new WeiboMultiMessage();
        TextObject tObj = new TextObject();

        tObj.text = data.wbTxt;
        tObj.text = tObj.text + " " + data.wbUrl;

        tObj.actionUrl = data.wbUrl;
        mMessage.textObject = tObj;

        ImageObject iObj = new ImageObject();
        Bitmap thumb = ImageUtil.getBitmap(R.drawable.logo, 100);
        Bitmap img = ImageUtil.getBitmap(R.drawable.logo, 150);

        iObj.setThumbImage(thumb);
        iObj.setImageObject(img);
        mMessage.imageObject = iObj;


        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = mMessage;


        boolean result = mWeiboShareAPI.sendRequest(mActivity, request);
        if (!result) {
            mToast.showToast("微博分享失败");
        }
        return result;
    }

    public boolean shareToQZone(ShareData data) {
        if (!isPkgInstalled("com.tencent.mobileqq") && !isPkgInstalled("com.qzone")) {
            mToast.showToast("QQ空间未安装");
        }
        final Bundle bundle = new Bundle();
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, ZXApplicationDelegate.getApplication().getString(R.string.app_name));
        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, data.qqTxt);

        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add(IMG_URL);

        bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);

        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, data.qqUrl);
        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);

        mTencent.shareToQzone(mActivity, bundle, mQQUiListener);
        return true;
    }

    private static boolean isPkgInstalled(String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = ZXApplicationDelegate.getApplication().getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean shareToQQ(ShareData data) {
        if (!isPkgInstalled("com.tencent.mobileqq")) {
            mToast.showToast("QQ未安装");
        }
        Bundle bundle = new Bundle();
        // 这条分享消息被好友点击后的跳转URL。
        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, data.qqUrl);
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, data.qqTxt);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, mActivity.getResources().getString(R.string.app_name));
        // 分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, IMG_URL);

        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, ZXApplicationDelegate.getApplication().getString(R.string.app_name));

        mTencent.shareToQQ(mActivity, bundle, mQQUiListener);
        return true;
    }

    public boolean shareToWeiXinTimeLine(ShareData data) {
        return shareCommonWeiXin(SendMessageToWX.Req.WXSceneTimeline, data);
    }

    public boolean shareToWeiXin(ShareData data) {
        return shareCommonWeiXin(SendMessageToWX.Req.WXSceneSession, data);
    }


    private boolean shareCommonWeiXin(int sence, ShareData data) {
        if (!mWXApi.isWXAppInstalled()) {
            mToast.showToast("微信未安装");
            return false;
        }
        if (sence == SendMessageToWX.Req.WXSceneTimeline && WX_FRIEND_SUPPORT == false) {
            mToast.showToast("微信朋友圈不支持！");
            return false;
        }

        WXMediaMessage msg = new WXMediaMessage();

        WXWebpageObject webObject = new WXWebpageObject();
        if (sence == SendMessageToWX.Req.WXSceneTimeline) {
            msg.description = data.mmTxt;
            msg.title = data.mmTxt;
            webObject.webpageUrl = data.mmUrl;

        } else {
            msg.description = data.wxTxt;
            msg.title = data.wxUrl;
            webObject.webpageUrl = data.wxUrl;
        }

        Bitmap thumb = ImageUtil.getBitmap(R.drawable.logo, 100);
        msg.thumbData = ImageUtil.bitmap2Byte(thumb, 80);

        msg.mediaObject = webObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = sence;
        return mWXApi.sendReq(req);
    }

    private IUiListener mQQUiListener = new IUiListener() {

        @Override
        public void onError(UiError arg0) {
            BdLog.e(arg0.errorMessage + arg0.errorMessage);
            mToast.showToast(arg0.errorMessage);
        }

        @Override
        public void onComplete(Object arg0) {
            mToast.showToast("分享成功");
            dealSucc();
        }

        @Override
        public void onCancel() {
            mToast.showToast("分享取消");
        }
    };

    public void dealSucc() {

        BdLog.d("ShareMananger share succ from=" + mFrom);
    }


}

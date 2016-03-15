package com.wazxb.xuerongbao.common.share;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.storage.data.ShareData;

/**
 * Created by zhengxin on 15/6/1.
 */
public class CommonShareDialog implements View.OnClickListener {

    private final Context mContext;
    private View mRootView;

    private NewShareProxy mMananger;
    private ShareData mShareData;

    // 6个icon
    private TextView mWeixinTimelineIcon;
    private TextView mWeixinIcon;
    private TextView mWeiboIcon;
    private TextView mQQIcon;
    private TextView mQzoneIcon;
    private TextView mCopyIcon;
    private View mCancelShare;

    private AlertDialog mDialog;

    private int mFrom = -1;


    public CommonShareDialog(Context context) {
        mContext = context;
        mMananger = new NewShareProxy((Activity) context);
        show();
    }

    public CommonShareDialog(Context context, int from) {
        mContext = context;
        mMananger = new NewShareProxy((Activity) context);
        mFrom = from;
        show();
    }


    public void setShareData(final ShareData shareData) {
        mShareData = shareData;
    }

    private void show() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.share_dialog_content, null);

        // 取消按钮
        mCancelShare = mRootView.findViewById(R.id.btnShareCancel);
        mCancelShare.setOnClickListener(this);

        // 分享到微信朋友圈
        mWeixinTimelineIcon = (TextView) mRootView.findViewById(R.id.share_weixin_friend_view);
        mWeixinTimelineIcon.setOnClickListener(this);

        // 分享到微信
        mWeixinIcon = (TextView) mRootView.findViewById(R.id.share_weixin_view);
        mWeixinIcon.setOnClickListener(this);

        // 分享到qzone
        mWeiboIcon = (TextView) mRootView.findViewById(R.id.share_weibo_view);
        mWeiboIcon.setOnClickListener(this);

        // 分享到腾讯微博
        mQQIcon = (TextView) mRootView.findViewById(R.id.share_qq_friend_view);
        mQQIcon.setOnClickListener(this);

        // 分享到新浪微博
        mQzoneIcon = (TextView) mRootView.findViewById(R.id.share_qzone_view);
        mQzoneIcon.setOnClickListener(this);

        mCopyIcon = (TextView) mRootView.findViewById(R.id.share_copy_view);
        mCopyIcon.setVisibility(View.INVISIBLE);


        if (mDialog == null) {
            mDialog = new AlertDialog.Builder(mContext).create();
            mDialog.setCanceledOnTouchOutside(true);
        }
        Window window = mDialog.getWindow();

        if (window == null) {
            mDialog = null;
            return;
        }

        mDialog.show();

        window.setWindowAnimations(R.style.living_share_dialog);//自下而上出弹窗
        window.setGravity(Gravity.BOTTOM);

        // 适应屏幕宽度
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // 视图填充
        // 设置监听，包括成功、失败、取消时候的动作
        WindowManager.LayoutParams lp = window.getAttributes();

//        lp.width = ZXViewHelper.getScreenW();
        window.setAttributes(lp);
        window.setContentView(mRootView);


    }

    @Override
    public void onClick(View view) {
        boolean result = false;
        if (view == mWeixinIcon) {
            result = mMananger.shareToWeiXin(mShareData);
        } else if (view == mWeixinTimelineIcon) {
            result = mMananger.shareToWeiXinTimeLine(mShareData);
        } else if (view == mWeiboIcon) {
            result = mMananger.shareToWeibo(mShareData);
        } else if (view == mQQIcon) {
            result = mMananger.shareToQQ(mShareData);
        } else if (view == mQzoneIcon) {
            result = mMananger.shareToQZone(mShareData);
        }
        if (!result) {
//            showToast("分享失败");
        }
        mDialog.dismiss();
    }


}

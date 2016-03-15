package com.wazxb.xuerongbao.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.common.share.ShareConfig;

import java.lang.ref.WeakReference;

public class WXEntryActivity extends ZXBBaseActivity implements IWXAPIEventHandler {
    private IWXAPI api = null;

    public interface IWxCallback {
        void onResp(BaseResp resp);
    }


    private static WeakReference<IWxCallback> mCallback;

    public static void setWxCallback(IWxCallback callback) {
        mCallback = new WeakReference<IWxCallback>(callback);
    }

    @Override
    public void onReq(BaseReq arg0) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWXAPI api = WXAPIFactory.createWXAPI(this, ShareConfig.WXAPPID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (api != null) {
            api.handleIntent(intent, this);
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String result = null;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "支付成功";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "支付取消";
                    break;
                default:
                    result = "支付失败";
                    break;

            }
            showToast(result);
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            if (mCallback != null) {
                IWxCallback callback = mCallback.get();
                if (callback != null) {
                    callback.onResp(resp);
                }
            }
        } else {
            String result;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "分享成功";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "分享取消";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "认证失败";
                    break;
                default:
                    result = "分享失败";
                    break;
            }

            showToast(result);
            if (mCallback != null) {
                IWxCallback callback = mCallback.get();
                if (callback != null) {
                    callback.onResp(resp);
                }
            }
        }
        this.finish();

    }

}

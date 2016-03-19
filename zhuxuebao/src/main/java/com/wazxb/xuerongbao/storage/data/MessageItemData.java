package com.wazxb.xuerongbao.storage.data;

import android.content.Context;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.moudles.credit.CreditActivity;
import com.wazxb.xuerongbao.moudles.evaluate.EvaluateSubmitActivity;
import com.wazxb.xuerongbao.moudles.message.MessageItemActivity;
import com.wazxb.xuerongbao.moudles.message.MessageManager;
import com.wazxb.xuerongbao.moudles.payback.PaybackActivity;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXJsonUtil;
import com.zxzx74147.devlib.utils.ZXStringUtil;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/13.
 */
public class MessageItemData implements Serializable {
    public int mId;   //消息ID
    public int mType;   //消息类型:
    //    1:系统消息；
//            2:逾期提醒，跳转到还款页；
//            3:审核提醒，跳转到信用额度页；
//            4:收款提醒，跳转到贷款历史页；
//            5:放款提醒，跳转到贷款历史页；
//            6:评价提醒，跳转到贷款评价页；
    public int contentType;   //内容类型，0:文字，1:url（包括客户端本地跳转的url）
    public int time;   //消息时间
    public String title;     //消息标题
    public String content;     //消息内容
    public String url;     //消息url（mType==6时，客
    public boolean mHasRead = false;

    public int getDrawableId() {
        int result = 0;
        switch (mType) {
            case 3:
                result = R.drawable.msg_audit;
                break;
            case 5:
                result = R.drawable.msg_apply;
                break;
            case 6:
                result = R.drawable.msg_evaluate;
                break;
            default:
                result = R.drawable.msg_notice;
        }
        return result;
    }


    public void onClick(View v) {
        mHasRead = true;
        MessageManager.sharedInstance().saveMsg();
        Context context = v.getContext();
        LoanItemData loan = null;
        if (ZXStringUtil.checkString(url)) {
            url = url.replaceFirst("comments://", "");
            if (url.startsWith("loan#")) {
                url = url.replace("loan#", "");
                loan = ZXJsonUtil.fromJsonString(url, LoanItemData.class);
            }
        }
        if (!mHasRead) {
            mHasRead = true;
            MessageManager.sharedInstance().saveMsg();
        }
        switch (mType) {
            case 1:
                ZXActivityJumpHelper.startActivity(context, MessageItemActivity.class, this);
                break;
            case 2:
                ZXActivityJumpHelper.startActivity(context, PaybackActivity.class);
                break;
            case 3:
                ZXActivityJumpHelper.startActivity(context, CreditActivity.class);
                break;
            case 4:
                ZXActivityJumpHelper.startActivity(context, CreditActivity.class);
                break;
            case 5:
                ZXActivityJumpHelper.startActivity(context, CreditActivity.class);
                break;
            case 6:
                if (loan != null) {
                    ZXActivityJumpHelper.startActivity(context, EvaluateSubmitActivity.class, loan);
                }
                break;
        }
    }
}

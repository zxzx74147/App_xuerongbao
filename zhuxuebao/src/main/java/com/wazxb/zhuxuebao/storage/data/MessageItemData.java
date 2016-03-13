package com.wazxb.zhuxuebao.storage.data;

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
}

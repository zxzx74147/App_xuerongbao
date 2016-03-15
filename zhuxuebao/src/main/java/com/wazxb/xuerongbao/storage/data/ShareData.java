package com.wazxb.xuerongbao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/3.
 */
public class ShareData implements Serializable {
    public String wbTxt;//微博分享文案
    public String wbUrl;//微博分享Url
    public String wxTxt;//微信分享文案
    public String wxUrl;//微信分享Url
    public String mmTxt;//朋友圈分享文案
    public String mmUrl;//朋友圈分享Url
    public String qqTxt;//QQ分享文案
    public String qqUrl;//QQ分享Url
}

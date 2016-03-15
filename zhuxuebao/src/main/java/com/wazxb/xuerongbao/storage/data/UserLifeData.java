package com.wazxb.xuerongbao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/5.
 */
public class UserLifeData implements Serializable {

   public int audit           ;   //审核状态：0：无审核，1：审核中，2：审核通过，4：审核失败
   public String dCardRecptPic   ;     //饭卡收据照片（picUrl）
    public String phInfoPic       ;     //手机号码网站截图（picUrl）
    public String  bankPics        ;     //银行流水截图（picUrls，用英文逗号分割）
    public String  taName          ;     //老师A姓名
    public int  taSex           ;   //老师A性别（1:男，2:女）
    public String taPhone         ;     //老师A电话
    public String  taDuty          ;     //老师A职务
    public String  tbName          ;     //老师B姓名
    public int  tbSex           ;   //老师B性别（1:男，2:女）
    public String  tbPhone         ;     //老师B电话
    public String  tbDuty          ;     //老师B职务
}

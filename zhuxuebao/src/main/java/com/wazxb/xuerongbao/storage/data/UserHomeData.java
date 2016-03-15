package com.wazxb.xuerongbao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/5.
 */
public class UserHomeData implements Serializable {

    public int audit;      //审核状态：0：无审核，1：审核中，2：审核通过，4：审核失败
    public String faName;         //父亲姓名
    public String faPhone;          //父亲电话
    public String moName;          //母亲姓名
    public String moPhone;          //母亲电话
}

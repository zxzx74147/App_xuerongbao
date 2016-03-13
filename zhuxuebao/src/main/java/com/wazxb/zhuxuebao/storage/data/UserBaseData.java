package com.wazxb.zhuxuebao.storage.data;

import com.zxzx74147.devlib.utils.ZXStringUtil;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/1.
 */
public class UserBaseData implements Serializable {
    public String udId;     //用户数字Id（用户账号）
    public String phone;     //手机号
    public String name;     //姓名
    public int sex;   //性别（1:男，2:女）
    public long quotaTotal;   //总额度
    public long quota;   //贷款额度
    public int beans;   //信用豆
    public String portrait;     //头像（picUrl）
    public String bank;     //银行名称
    public String bankBranch;     //开户行名称
    public String bankCard;     //银行卡号

    public String getBankCard() {
        if (ZXStringUtil.checkString(bankCard) && bankCard.length() >= 4) {
            return bankCard.substring(bankCard.length() - 4);
        }
        return bankCard;
    }
}

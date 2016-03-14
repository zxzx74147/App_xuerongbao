package com.wazxb.zhuxuebao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/14.
 */
public class CoinData implements Serializable {
    public int isSigned;   //是否已签到，0:未签到，1:已签到
    public int days;   //连续签到天数，至少为1
    public int coins;   //本次签到金币数
    public int totalCoins;   //用户总金币数
}

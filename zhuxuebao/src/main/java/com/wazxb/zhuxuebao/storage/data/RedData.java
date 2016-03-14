package com.wazxb.zhuxuebao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/14.
 */
public class RedData implements Serializable {
    public int grabed;   //是否已抢过，0:未抢过，1:已抢过
    public int money;   //红包金额
    public int left;   //剩余红宝数，最小为0，为0时，表示本次红包已结束
    public int grabMoney;   //抢到
}

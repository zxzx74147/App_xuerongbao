package com.wazxb.xuerongbao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/10.
 */
public class BorrowRequestData implements Serializable {
    public int lnProdId;        //贷款产品ID（1：福利贷，2：活利贷，3：月利贷）
    public int money;        //贷款钱数
    public int day;        //贷款天数
    public int month;        //贷款月数
    public String usage;        //借款用途
    public String source;        //还款来源
    public float returnNum;        //还款来源

    public ProdData mProdData;
}

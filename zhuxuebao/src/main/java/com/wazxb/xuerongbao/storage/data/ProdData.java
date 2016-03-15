package com.wazxb.xuerongbao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/10.
 */
public class ProdData implements Serializable {

    public int lnProdId;    //贷款产品ID（1：福利贷，2：活利贷，3：月利贷）
    public int type;    //产品类型：0:随借随还，1:等额本息
    public String name;         //贷款产品名
    public String rate;         //贷款利率
    public float rateFlt;        //贷款利率（float）
    public String consultRate;      //平台费
    public float consultRateFlt;       //平台费（float）
    public String bondRate;         //保证金
    public float bondRateFlt;         //保证金（float）
    public String lateRate;        //逾期利率
    public String lateRateFlt;        //逾期利率（float）
    public int minMoney;     //最小可借多少钱(单位：1元)
    public int maxMoney;      //最多可借多少钱(单位：1元)
    public int minDay;       //最短贷款天数（最小为0）
    public int maxDay;       //最长贷款天数（最小为1）
    public int minMonth;       //最短贷款月数（最小为0）
    public int maxMonth;      //最长贷款月数（最小为1）
    public String intro;            //贷款产品说明
    public String lateIntro;         //逾期说明
}

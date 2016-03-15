package com.wazxb.xuerongbao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/11.
 */
public class LoanItemData implements Serializable {
    public int lnId;   //贷款ID
    public int lnProdId;   //贷款产品ID（1：福利贷，2：活利贷，3：月利贷）
    public int type;   //贷款类型：0:随借随还，1:等额本息
    public int status;   //贷款状态（0:待审核、1:审核不通过、2:还款中、3:已还完、4:已逾期）
    public String name;     //贷款产品名
    public int money;   //本金
    public int day;   //贷款天数
    public int month;   //贷款月数
    public String usage;     //贷款用途
    public String source;     //还款来源
    public String contPics;     //合同招聘（picKeys，用英文逗号分割）
    public String returnMoney;     //已还本金（float数字）
    //（当status==2/4&&type==0时，returnMoney/money，计算进度）
    public int returnMonth;   //已还期数
    //（当status==2/4&&type==1时，returnMonth/month，计算进度）
    public String returnAver;     //每期金额（float数字）
    public String returnTotal;     //剩余应还总金额（float数字）
    public int returnTime;   //还款时间
    public int lnTime;   //贷款时间

    public LoanHisListData hisList;

    public String getMoneyShow() {
        return "申请额度￥ " + money;
    }

    public int getProgress()

    {
        return (int) (Float.valueOf(returnMoney) / Float.valueOf(money) * 100);
    }

    public String getStatusString() {
        switch (status) {
            case 0:
                return "待审核";
            case 1:
                return "审核不通过";
            case 2:
                return "还款中";
            case 3:
                return "已还完";
            case 4:
                return "已逾期";

        }
        return null;
    }
}

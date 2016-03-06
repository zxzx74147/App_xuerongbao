package com.wazxb.zhuxuebao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/5.
 */
public class UserSchoolData implements Serializable {

    public int audit;   //审核状态：0：无审核，1：审核中，2：审核通过，4：审核失败
    public String caName;     //同学A姓名
    public int caSex;   //同学A性别（1:男，2:女）
    public String caPhone;     //同学A电话
    public String caDormAddr;     //同学A寝室地址
    public String cbName;     //同学B姓名
    public int cbSex;   //同学B性别（1:男，2:女）
    public String cbPhone;     //同学B电话
    public String cbDormAddr;     //同学B寝室地址
    public String faCompany;     //父亲工作单位
    public String faCompanyAddr;     //父亲工作单位地址
    public String faDuty;     //父亲工作职责
    public String moCompany;     //母亲工作单位
    public String moCompanyAddr;     //母亲工作单位地址
    public String moDuty;     //母亲工作职责
    public String homeAddr;     //父母居住地址
    public String admissionPic;     //入学通知书照片（picUrl）
    public String handPic;     //手持身份证照片（picUrl）
}

package com.wazxb.zhuxuebao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/5.
 */
public class UserCdData implements Serializable {

    public int audit;   //审核状态：0：无审核，1：审核中，2：审核通过，4：审核失败
    public String name;     //姓名
    public int sex;   //性别（1:男，2:女）
    public String birthday;     //生日（格式：2000-01-01）
    public String pID;     //身份证号（18位）
    public String pIDFPic;     //身份证 正面照片（picUrl）
    public String pIDBPic;     //身份证 反面照片（picUrl）
    public String place;     //籍贯
    public String university;     //学校
    public String college;     //学院
    public String major;     //专业
    public String grade;     //班级
    public String education;     //学历
    public String enrollment;     //入学时间（格式：2000-01）
    public String sID;     //学生证号
    public String sIDAPic;     //学生证 照片A（picUrl）
    public String sIDBPic;     //学生证 照片B（picUrl）
    public String sCardFPic;     //一卡通 正面照片（picUrl）
    public String sCardBPic;     //一卡通 反面照片（picUrl）
    public String authenPic;     //学信网照片（picUrl）
    public String dorm;     //宿舍地址
    public String qq;     //QQ号
    public String qqToken;     //QQ绑定Token
    public String weibo;     //option: 微博（格式:chenzhentongxue）
    public String weiboToken;     //option：微博绑定Token
    public String weixin;     //微信
    public String weixinToken;     //微信绑定Token
    public String mail;     //邮箱
}

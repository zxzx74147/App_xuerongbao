package com.wazxb.zhuxuebao.storage.data;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/5.
 */
public class UserAllData implements Serializable{
    public UserBaseData user;
    public UserCdData cdBase;
    public UserSchoolData cdSchool;
    public UserLifeData cdLife;
    public UserHomeData cdHome;
}

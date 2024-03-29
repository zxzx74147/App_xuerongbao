package com.wazxb.xuerongbao.storage.data;

import android.view.View;

import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.moudles.coin.CoinAddressActivity;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;

import java.io.Serializable;

/**
 * Created by zhengxin on 16/3/13.
 */
public class GiftItemData implements Serializable {
    public int itemId;   //商品ID
    public String name;     //商品名称
    public String content;     //商品描述
    public String picUrl;     //商品图片（picUrl）
    public int coinNum;   //金币价格

    public void onClick(View v){
        UserAllData user = AccountManager.sharedInstance().getUserAllData();
        if(user.user.coins<coinNum){
            ((ZXBBaseActivity)v.getContext()).showToast("金币不足");
            return;
        }
        ZXActivityJumpHelper.startActivity(v.getContext(), CoinAddressActivity.class,this);
    }
}

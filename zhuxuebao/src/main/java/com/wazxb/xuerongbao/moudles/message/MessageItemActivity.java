package com.wazxb.xuerongbao.moudles.message;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityMessageItemBinding;
import com.wazxb.xuerongbao.storage.data.MessageItemData;

/**
 * Created by zhengxin on 16/3/6.
 */
public class MessageItemActivity extends ZXBBaseActivity {

    private ActivityMessageItemBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_message_item);
        mBinding.setHandler(this);
        MessageItemData data = (MessageItemData) getParam();
        mBinding.setData(data);
        mBinding.titleBar.setText(data.title);
    }
}

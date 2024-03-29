package com.wazxb.xuerongbao.moudles.more;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityContractBinding;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.InitData;
import com.zxzx74147.devlib.utils.ZXDialogUtil;

/**
 * Created by zhengxin on 16/3/6.
 */
public class ContractActivity extends ZXBBaseActivity {

    private ActivityContractBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_contract);
        InitData init = StorageManager.sharedInstance().getInitdat();
        mBinding.setHandler(this);

        mBinding.setData(init);
    }

    public void onPhoneClick(View v) {
        final InitData init = StorageManager.sharedInstance().getInitdat();
        ZXDialogUtil.showCheckDialog(this, init.contact.tele, new Runnable() {
                    @Override
                    public void run() {
                        // 使用系统的电话拨号服务，必须去声明权限，在AndroidManifest.xml中进行声明
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                                + init.contact.tele));
                        startActivity(intent);
                    }
                });


    }
}

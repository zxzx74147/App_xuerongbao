package com.wazxb.xuerongbao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.databinding.ActivityLoginBinding;
import com.zxzx74147.devlib.base.ZXBaseActivity;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;

public class ChangePasswordActivity extends ZXBaseActivity {
    ActivityLoginBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        mBinding.titleBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZXActivityJumpHelper.startActivity(ChangePasswordActivity.this, RegitsterActivity.class);
            }
        });
    }

    public void onLoginClick(View view) {

    }

    public void onForgetClick(View view) {

    }


    public void login() {
        String password = mBinding.passwordId.getText();
        String phoneNum = mBinding.passwordId.getText();
    }
}

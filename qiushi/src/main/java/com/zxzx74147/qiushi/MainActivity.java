package com.zxzx74147.qiushi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.qiushi.module.frs.FrsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ZXActivityJumpHelper.startActivity(this, FrsActivity.class);

    }


}

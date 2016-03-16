package com.wazxb.xuerongbao.moudles;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.common.webview.CommonWebActivity;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.InitData;
import com.wazxb.xuerongbao.storage.data.SplashData;
import com.wazxb.xuerongbao.util.ImageUtil;
import com.zxzx74147.devlib.utils.SharedPreferenceHelper;

import java.util.List;

public class LogoActivity extends ZXBBaseActivity {

    private int mIndex = 0;
    private ImageView mImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        mImage = (ImageView) findViewById(R.id.logo);
        InitData init = StorageManager.sharedInstance().getInitdat();
        mIndex = SharedPreferenceHelper.getInt("index", 0);
        if (init != null && init.ad != null && init.ad.splash != null && init.ad.splash.size() > 0) {
            List<SplashData> mSplash = init.ad.splash;
            mIndex %= mSplash.size();
            final SplashData data = mSplash.get(mIndex);
            mIndex++;
            SharedPreferenceHelper.saveInt("index", mIndex);
            ImageUtil.loadImage(data.picUrl, mImage);
            mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonWebActivity.startActivity(LogoActivity.this, null, data.url);
                }
            });
        }
        mImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
}




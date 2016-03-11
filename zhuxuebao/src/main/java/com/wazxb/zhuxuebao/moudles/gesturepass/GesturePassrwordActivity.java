package com.wazxb.zhuxuebao.moudles.gesturepass;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.eftimoff.patternview.PatternView;
import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityGesturePasswordBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.zxzx74147.devlib.utils.BdLog;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/3/7.
 */
public class GesturePassrwordActivity extends ZXBBaseActivity {

    private int mMode = MODE_SET;
    public static final int MODE_SET = 0;
    public static final int MODE_CHECK = 1;
    private String mOldPass = null;

    private ActivityGesturePasswordBinding mBinding = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMode = (int) getParam();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_gesture_password);
//        mBinding.patternView.setDefaultBitmap(R.drawable.gesture_null);
//        mBinding.patternView.setSelectedBitmap(R.drawable.gesture_selected);
        mBinding.patternView.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            @Override
            public void onPatternDetected() {
                String pass = mBinding.patternView.getPatternString();
                if (!ZXStringUtil.checkString(pass)) {
                    BdLog.e("PASS=" + pass);
                    return;
                }
                switch (mMode) {
                    case MODE_CHECK:
                        if (pass.equals(AccountManager.sharedInstance().getPassword())) {
                            postRunnableDelyed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 400);
                        }
                        break;
                    case MODE_SET:
                        if (!ZXStringUtil.checkString(mOldPass)) {
                            mOldPass = pass;
                            mBinding.patternView.clearPattern(300);
                            return;
                        }
                        if (mOldPass.equals(pass)) {
                            AccountManager.sharedInstance().setPassword(pass);
                            postRunnableDelyed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 400);

                        } else {
                            showToast("两次输入不一致");
                            mOldPass = null;
                        }
                        break;
                }


            }
        });
    }
}

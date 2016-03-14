package com.wazxb.zhuxuebao.moudles.gesturepass;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alibaba.sdk.android.util.Md5Utils;
import com.eftimoff.patternview.PatternView;
import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityGesturePasswordBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
import com.zxzx74147.devlib.utils.BdLog;
import com.zxzx74147.devlib.utils.SharedPreferenceHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/3/7.
 */
public class GesturePassrwordActivity extends ZXBBaseActivity {

    private int mMode = MODE_SET;
    public static final int MODE_SET = 0;
    public static final int MODE_CHECK = 1;
    private String mOldPass = null;
    private int mLastNum = 5;
    private ActivityGesturePasswordBinding mBinding = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMode = (int) getParam();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_gesture_password);
        mBinding.setHandler(this);
        mLastNum = SharedPreferenceHelper.getInt("pass_last_num", 5);
        UserAllData data = AccountManager.sharedInstance().getUserAllData();
        if (data != null) {
            mBinding.setData(data.user);
        }
        mBinding.patternView.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            @Override
            public void onPatternDetected() {
                String pass = mBinding.patternView.getPatternString();
                if (!ZXStringUtil.checkString(pass)) {
                    BdLog.e("PASS=" + pass);
                    return;
                }
                String[] splis = pass.split("&");
                StringBuilder sb = new StringBuilder();
                for (String item : splis) {
                    String[] temp = item.split("-");
                    if (temp.length != 2) {
                        continue;
                    }
                    int temp1 = Integer.valueOf(temp[0], 10);
                    int temp2 = Integer.valueOf(temp[1], 10);
                    if (sb.length() != 0) {
                        sb.append(",");
                    }
                    sb.append(String.valueOf(temp1 * 3 + temp2));
                }
                pass = Md5Utils.md5Digest(sb.toString().getBytes());
                mBinding.patternView.clearPattern(300);
                switch (mMode) {
                    case MODE_CHECK:
                        if (pass.equals(AccountManager.sharedInstance().getPassword())) {
                            mBinding.remind.setText(R.string.pass_check_succ);
                            SharedPreferenceHelper.saveInt("pass_last_num", 5);
                            postRunnableDelyed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 200);
                        } else {
                            if (mLastNum == 0) {
                                AccountManager.sharedInstance().logout();
                                finish();
                                return;
                            }
                            mBinding.remind.setText(String.format(getResources().getString(R.string.pass_error), mLastNum));
                            mLastNum--;
                        }
                        break;
                    case MODE_SET:
                        if (!ZXStringUtil.checkString(mOldPass)) {
                            mBinding.remind.setText(R.string.pass_second);
                            mOldPass = pass;
                            return;
                        }
                        if (mOldPass.equals(pass)) {
                            mBinding.remind.setText(R.string.pass_succ);
                            AccountManager.sharedInstance().setPassword(pass);
                            postRunnableDelyed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 400);

                        } else {
                            mBinding.remind.setText(R.string.pass_second_error);
                            mOldPass = null;
                        }
                        break;
                }


            }
        });
    }
}

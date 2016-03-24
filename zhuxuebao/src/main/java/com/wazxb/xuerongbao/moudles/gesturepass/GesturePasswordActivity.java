package com.wazxb.xuerongbao.moudles.gesturepass;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.sdk.android.util.Md5Utils;
import com.eftimoff.patternview.PatternView;
import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityGesturePasswordBinding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.data.UidData;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.BdLog;
import com.zxzx74147.devlib.utils.SharedPreferenceHelper;
import com.zxzx74147.devlib.utils.ZXDialogUtil;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/3/7.
 */
public class GesturePasswordActivity extends ZXBBaseActivity {

    private int mMode = MODE_SET;
    public static final int MODE_SET = 0;
    public static final int MODE_CHECK = 1;
    private String mOldPass = null;
    private int mLastNum = 5;
    private ActivityGesturePasswordBinding mBinding = null;
    private ZXBHttpRequest mRequest = null;


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

        if (mMode == MODE_CHECK) {
            mBinding.titleBar.hideBack();
        }
        mBinding.patternView.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            @Override
            public void onPatternDetected() {
                String pass = mBinding.patternView.getPatternString();
                mBinding.patternView.clearPattern(300);
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
                        if (splis.length < 4) {
                            mBinding.remind.setText(R.string.pass_small_error);
                            return;
                        }
                        if (!ZXStringUtil.checkString(mOldPass)) {
                            mBinding.remind.setText(R.string.pass_second);
                            mOldPass = pass;
                            return;
                        }

                        if (mOldPass.equals(pass)) {
                            mBinding.remind.setText(R.string.pass_succ);

                            submit(pass);


                        } else {
                            mBinding.remind.setText(R.string.pass_second_error);
                            mOldPass = null;
                        }
                        break;
                }


            }
        });
    }

    public void submit(final String pass) {
        if (mRequest != null) {
            mRequest.cancel();
        }
        mRequest = new ZXBHttpRequest<>(UidData.class, new HttpResponseListener<UidData>() {
            @Override
            public void onResponse(HttpResponse<UidData> response) {
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().setPassword(pass);
                postRunnableDelyed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 200);
            }
        });
        mRequest.addParams("gesture", pass);
        mRequest.setPath(NetworkConfig.ADDRESS_U_GESTURE);
        sendRequest(mRequest);
    }

    public void onForgetpassword(View v) {
        ZXDialogUtil.showCheckDialog(this, R.string.forget_pass_remind, new Runnable() {
            @Override
            public void run() {
                AccountManager.sharedInstance().logout();
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        try {
            return super.onKeyDown(keyCode, event);
        } catch (IllegalStateException e) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
            }
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mMode == MODE_CHECK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                moveTaskToBack(true);
                return true;
            }
        }
        try {
            return super.onKeyUp(keyCode, event);
        } catch (IllegalStateException e) {
            finish();
        }

        return true;
    }
}

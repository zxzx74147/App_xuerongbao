package com.wazxb.xuerongbao.base;

import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.util.ActivityStateManager;
import com.wazxb.xuerongbao.util.RequestCode;
import com.wazxb.xuerongbao.widget.InputTextView;
import com.wazxb.xuerongbao.widget.UploadImageView;
import com.zxzx74147.devlib.base.ZXBaseActivity;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.utils.ZXViewHelper;

/**
 * Created by zhengxin on 16/2/27.
 */
public class ZXBBaseActivity<T> extends ZXBaseActivity {

    private ImageView mProgressBar;
    private TextView mEmptyView = null;

    protected T getParam() {
        return (T) getIntent().getSerializableExtra(ZXActivityJumpHelper.INTENT_DATA);
    }

    /**
     * 中心点为基点显示菊花
     */
    public void showProgressBar() {
        showProgressBarWithOffset(0, 0);
    }


    public void showProgressBarWithOffset(int xDp, int yDp) {
        if (mProgressBar == null) {
            mProgressBar = new ImageView(this);
            mProgressBar.setImageResource(R.drawable.progress);
            FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
            content.addView(mProgressBar,
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        }
        mProgressBar.setPadding(ZXViewHelper.dip2px(this, xDp), ZXViewHelper.dip2px(this, yDp), 0, 0);
        mProgressBar.setVisibility(View.VISIBLE);
        ZXViewHelper.startFramAnim(mProgressBar);
    }

    public void showEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = new TextView(this);
            mEmptyView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.error_tip, 0, 0);
            mEmptyView.setCompoundDrawablePadding(20);
            mEmptyView.setGravity(Gravity.CENTER_HORIZONTAL);
            mEmptyView.setTextColor(getResources().getColor(R.color.text_color_grey));
            int id = getEmptyStringID();
            if(id>0){
                mEmptyView.setText(id);
            }
            FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
            content.addView(mEmptyView,
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        }
        mEmptyView.setVisibility(View.VISIBLE);
    }

    public void hideEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏菊花
     */
    public void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
            ZXViewHelper.stopFramAnim(mProgressBar);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode / RequestCode.REQUEST_PICK_PHOTO == 1) {
            View root = getWindow().getDecorView();
            final Uri uri = data.getData();
            if (uri == null) {
                return;
            }
            ZXViewHelper.dfsViewGroup(root, new ZXViewHelper.IViewProcess() {
                @Override
                public void processView(View view) {
                    if (view instanceof UploadImageView) {
                        ((UploadImageView) view).onPicSelected(requestCode, uri);
                    }
                }
            });
        }
        if (requestCode / RequestCode.REQUEST_PICK_PHONE == 1) {
            View root = getWindow().getDecorView();
            final Uri uri = data.getData();
            if (uri == null) {
                return;
            }
            ZXViewHelper.dfsViewGroup(root, new ZXViewHelper.IViewProcess() {
                @Override
                public void processView(View view) {
                    if (view instanceof InputTextView) {
                        ((InputTextView) view).onPhoneSelected(requestCode, uri);
                    }
                }
            });
        }

        if (requestCode / RequestCode.REQUEST_MSG_SCHOOL == 1) {
            View root = getWindow().getDecorView();
            final String school = data.getStringExtra("data");
            if (school == null) {
                return;
            }
            ZXViewHelper.dfsViewGroup(root, new ZXViewHelper.IViewProcess() {
                @Override
                public void processView(View view) {
                    if (view instanceof InputTextView) {
                        ((InputTextView) view).onSchoolSelected(requestCode, school);
                    }
                }
            });
        }

    }

    public void postRunnableDelyed(Runnable runnable, int time) {
        getWindow().getDecorView().postDelayed(runnable, time);
    }

    @Override
    public void onPause() {
        super.onPause();
        ActivityStateManager.sharedInstance().onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityStateManager.sharedInstance().onResume(this);
    }

    protected int getEmptyStringID() {
        return 0;
    }
}

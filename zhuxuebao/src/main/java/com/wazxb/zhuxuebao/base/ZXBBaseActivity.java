package com.wazxb.zhuxuebao.base;

import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.util.RequestCode;
import com.wazxb.zhuxuebao.widget.InputTextView;
import com.wazxb.zhuxuebao.widget.UploadImageView;
import com.zxzx74147.devlib.base.ZXBaseActivity;
import com.zxzx74147.devlib.utils.ZXViewHelper;

/**
 * Created by zhengxin on 16/2/27.
 */
public class ZXBBaseActivity extends ZXBaseActivity {

    private ImageView mProgressBar;

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

    }
}

package com.wazxb.xuerongbao.common.portrait;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.common.portrait.widget.RectSelectImageView;
import com.wazxb.xuerongbao.util.ImageUtil;
import com.zxzx74147.devlib.widget.TitleBar;


/**
 * Created by zhengxin on 14/10/24.
 */
public class EditPortraitActivity extends ZXBBaseActivity {
    public static final int MODE_UPLOAD = 0;
    public static final int MODE_PORTRAIT = 1;
    public static final int MODE_FRIEND_PORTRAIT = 2;
    public static final int MODE_SELECT = 3;
    private static final String KEY_PATH = "path";
    private static final String KEY_MODE = "mode";

    private RectSelectImageView mRectSelectView = null;
    private TitleBar mTitleBar = null;
    private String picPath = null;
    private Uri mPicUri = null;
    private boolean isSending = false;
    private byte[] imgByte;
    private int mMode = MODE_UPLOAD;


    public static void startActivityForResult(final Activity activity, Uri path, final int requestCode, int mode) {
        Intent intent = new Intent(activity, EditPortraitActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        intent.setData(path);
        intent.putExtra(KEY_MODE, mode);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(final Fragment fragment, Uri path, final int requestCode, int mode) {
        Intent intent = new Intent(fragment.getActivity(), EditPortraitActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        intent.setData(path);
        intent.putExtra(KEY_MODE, mode);
        fragment.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_portrait_activity);
        mRectSelectView = (RectSelectImageView) findViewById(R.id.photo_editor);
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSending) {
                    return;
                }
                isSending = true;
                Bitmap bm = mRectSelectView.getSelectedImage();
                showProgressBar();
                ImageUtil.uploadImage(bm, new ImageUtil.ImageUploadCallback() {
                    @Override
                    public void onPrepare() {

                    }

                    @Override
                    public void onSucc(String picKey, String picUrl) {
                        hideProgressBar();
                        Intent intent = new Intent();
                        intent.putExtra("key", picKey);
                        intent.putExtra("url", picUrl);
                        setResult(Activity.RESULT_OK,intent);
                        EditPortraitActivity.super.finish();
                    }

                    @Override
                    public void onFail() {
                        showToast("图片上传失败！");
                        hideProgressBar();
                    }
                });
            }
        });
        mRectSelectView.setIsSelecting(true);
        initData(savedInstanceState);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_PATH, picPath);
        outState.putInt(KEY_MODE, mMode);
    }

    private void initData(Bundle savedInstanceState) {
        picPath = null;
        if (savedInstanceState != null) {
            picPath = savedInstanceState.getString(KEY_PATH);
            mMode = savedInstanceState.getInt(KEY_MODE);
        } else {
            Intent intent = getIntent();
            picPath = intent.getStringExtra(KEY_PATH);
            mMode = intent.getIntExtra(KEY_MODE, MODE_UPLOAD);
            mPicUri = intent.getData();
        }
        if (mPicUri != null) {
            ImageUtil.loadImage(mPicUri, mRectSelectView);

        } else {
            ImageUtil.loadImage(picPath, mRectSelectView);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void finish() {
        setResult(Activity.RESULT_CANCELED);
        super.finish();
    }
}

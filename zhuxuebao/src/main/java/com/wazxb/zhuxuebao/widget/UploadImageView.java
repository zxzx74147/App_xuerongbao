package com.wazxb.zhuxuebao.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.UploadImageviewBinding;
import com.wazxb.zhuxuebao.util.IDUtil;
import com.wazxb.zhuxuebao.util.ImageUtil;
import com.wazxb.zhuxuebao.util.RequestCode;
import com.wazxb.zhuxuebao.util.ZXUtil;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/2/27.
 */
public class UploadImageView extends RelativeLayout {


    private String mPicKey = null;
    private String mPicUrl = null;
    private UploadImageviewBinding mBinding = null;
    private Uri mPicUri = null;
    private String mPostKey = null;
    private boolean mIsNotNull = false;
    private final int PIC_REQUEST_ID = IDUtil.genID() + RequestCode.REQUEST_PICK_PHOTO;

    public UploadImageView(Context context) {
        super(context);
        init(null);
    }

    public UploadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public UploadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.upload_imageview, this, true);
        mBinding.setHandler(this);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.UploadImageView, 0, 0);
            String label = a.getString(R.styleable.UploadImageView_img_label_text);
            if (ZXStringUtil.checkString(label)) {
                mBinding.picHint.setText(label);
            }
            mPostKey = a.getString(R.styleable.UploadImageView_img_post_key);
            mIsNotNull = a.getBoolean(R.styleable.UploadImageView_img_not_null, false);
            mBinding.notNull.setVisibility(mIsNotNull ? VISIBLE : GONE);
            mPicUrl = a.getString(R.styleable.UploadImageView_img_url);
            if (ZXStringUtil.checkString(mPicUrl)) {
                ImageUtil.loadImage(mPicUrl, mBinding.picture);
            }
            a.recycle();
        }
    }

    public void setImageUrl(String url){
        mPicUrl = url;
        if (ZXStringUtil.checkString(mPicUrl)) {
            ImageUtil.loadImage(mPicUrl, mBinding.picture);
        }

    }
    public boolean checkDone() {
        if (!mIsNotNull) {
            return true;
        }
        if (!ZXStringUtil.checkString(mPicKey)) {
            return false;
        }
        return true;
    }

    public String getError() {
        return "请上传图片";
    }


    public String getPicKey() {
        return mPicKey;
    }

    public void onCheckClick(View v) {
        ZXUtil.takePhoto((Activity) getContext(), PIC_REQUEST_ID);
    }

    public void onPicSelected(int picId, Uri uri) {
        if (picId != PIC_REQUEST_ID) {
            return;
        }
        mPicKey = null;
        mPicUrl = null;
        mPicUri = uri;
        if (getContext() instanceof ZXBBaseActivity) {
            ((ZXBBaseActivity) getContext()).showProgressBar();
        }
        ImageUtil.uploadImage(uri, mBinding.picture, new ImageUtil.ImageUploadCallback() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSucc(String picKey, String picUrl) {
                mPicKey = picKey;
                mPicUrl = picUrl;
                if (getContext() instanceof ZXBBaseActivity) {
                    ((ZXBBaseActivity) getContext()).hideProgressBar();
                }
            }

            @Override
            public void onFail() {
                if (getContext() instanceof ZXBBaseActivity) {
                    ((ZXBBaseActivity) getContext()).hideProgressBar();
                    ((ZXBBaseActivity) getContext()).showToast("图片上传失败");
                }

            }
        });
    }

    public String getPostKey() {
        return mPostKey;
    }

}

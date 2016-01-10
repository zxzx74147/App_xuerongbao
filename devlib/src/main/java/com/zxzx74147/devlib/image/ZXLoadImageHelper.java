package com.zxzx74147.devlib.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.image.widget.ZXImageView;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/1/10.
 */
public class ZXLoadImageHelper {


    public static void loadImageView(String url, ImageView imageView) {

        if (ZXStringUtil.checkString(url)) {
            return;
        }
        if (imageView == null) {
            return;
        }
        if (imageView instanceof ZXImageView) {
            if (url.equals(((ZXImageView) imageView).getImageUrl())) {
                return;
            } else {
                imageView.setImageDrawable(null);
            }
        }
        Context context = imageView.getContext();
        if (context == null) {
            context = ZXApplicationDelegate.getApplication();
        }
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

}

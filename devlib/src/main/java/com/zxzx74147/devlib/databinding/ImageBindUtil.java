package com.zxzx74147.devlib.databinding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.image.widget.ZXImageView;
import com.zxzx74147.devlib.utils.ZXStringUtil;

//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.controller.BaseControllerListener;
//import com.facebook.drawee.controller.ControllerListener;
//import com.facebook.drawee.interfaces.DraweeController;

/**
 * Created by zhengxin on 15/9/25.
 */
public class ImageBindUtil {

    @BindingAdapter({"app:img_url"})
    public static void loadImage(ImageView imageView, String url) {
        if (!ZXStringUtil.checkString(url)) {
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
//        Glide.with(context)
//                .load(url)
//                .placeholder(R.drawable.ic_launcher)
//                .into(imageView);
        Picasso.with(imageView.getContext()).load(url).into(imageView);
//        if(url == null){
//            view.setController(null);
//            return;
//        }
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri(Uri.parse(url))
//                .setTapToRetryEnabled(true)
//                .setOldController(view.getController())
//                .setControllerListener(listener)
//                .build();
//
//        view.setController(controller);
    }

//    private static ControllerListener listener = new BaseControllerListener() {};

}

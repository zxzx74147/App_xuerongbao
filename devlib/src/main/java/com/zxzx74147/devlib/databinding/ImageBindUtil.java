package com.zxzx74147.devlib.databinding;

import android.databinding.BindingAdapter;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.zxzx74147.devlib.image.widget.ZXImageView;

/**
 * Created by zhengxin on 15/9/25.
 */
public class ImageBindUtil {

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ZXImageView view, String url) {
        if(url == null){
            view.setController(null);
            return;
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setTapToRetryEnabled(true)
                .setOldController(view.getController())
                .setControllerListener(listener)
                .build();

        view.setController(controller);
    }

    private static ControllerListener listener = new BaseControllerListener() {};

}

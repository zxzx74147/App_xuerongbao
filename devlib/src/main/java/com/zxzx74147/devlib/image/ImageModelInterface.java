package com.zxzx74147.devlib.image;

import android.content.Context;

import com.squareup.picasso.Picasso;

//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by zhengxin on 15/8/27.
 */
public class ImageModelInterface  {

    public static boolean init(Context context){
        initFresco(context);
        return true;
    }

    private static void initFresco(Context context){
//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context).build();
//        Fresco.initialize(context, config);
        Picasso.with(context);
    }
}

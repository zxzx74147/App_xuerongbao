package com.zxzx74147.qiushi.libs.util;

/**
 * Created by zhengxin on 15/9/28.
 */
public class ImageUtil {

    public static final String IMG_REG = "http://pic.qiushibaike.com/system/pictures/%1$s/%2$s/medium/%3$s";

    public static final String AVG_REG = "http://pic.qiushibaike.com/system/avtnew/%1$s/%2$s/medium/%3$s";

    public static String getImageUrl(String imageID){
        String temp = "";
        if(imageID == null){
            return null;
        }
        if(imageID.startsWith("app")&&imageID.lastIndexOf(".")>0){
            temp = imageID.substring(3,imageID.lastIndexOf("."));
            String result =  String.format(IMG_REG,temp.substring(0,5),temp,imageID);
            return result;
        }else{
            return null;
        }
    }

    public static String getAvgUrl(String id,String icon){

        if(id == null || icon == null){
            return null;
        }
        return String.format(AVG_REG,id.substring(0,id.length()-4),id,icon);
    }
}

package com.wazxb.zhuxuebao.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.network.ZXBRequestManageer;
import com.wazxb.zhuxuebao.network.http.BdHttpManager2;
import com.wazxb.zhuxuebao.network.http.HttpContext2;
import com.wazxb.zhuxuebao.network.http.HttpRequest2;
import com.wazxb.zhuxuebao.network.http.HttpResponsedMessage;
import com.wazxb.zhuxuebao.storage.data.UploadPicData;
import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.base.ZXBaseActivity;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.AsyncHelper;
import com.zxzx74147.devlib.utils.BdLog;
import com.zxzx74147.devlib.utils.ZXJsonUtil;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengxin on 16/2/27.
 */
public class ImageUtil {
    private static final String ADDRESS_UP_IMAGE = "sys/uppic";
    private static final int IMAGE_MAX_SIZE = 600;

    public interface ImageUploadCallback {
        void onPrepare();

        void onSucc(String picKey, String picUrl);

        void onFail();
    }


    public static boolean loadImage(Uri uri, ImageView imageView) {
        if (imageView == null) {
            return false;
        }
        Picasso.with(imageView.getContext())
                .load(uri)
                .resize(IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)
                .into(imageView);
        return true;
    }

    public static boolean loadImage(String path, ImageView imageView) {
        if (imageView == null) {
            return false;
        }
        Picasso.with(imageView.getContext())
                .load(path)
                .resize(IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)
                .into(imageView);
        return true;
    }

    /**
     * bitmap 转换图片为 byte[]，存为jpg格式
     *
     * @param bm
     * @return
     */
    public static byte[] bitmap2Byte(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return baos.toByteArray();
    }

    public static boolean uploadImage(Uri uri, final ImageView imageView, final ImageUploadCallback callback) {
        final Context context = imageView == null ? ZXApplicationDelegate.getApplication() : imageView.getContext();

        final HttpResponseListener<UploadPicData> listener = new HttpResponseListener<UploadPicData>() {
            @Override
            public void onResponse(HttpResponse<UploadPicData> response) {
                if (callback == null) {
                    return;
                }
                if (response.error != 0) {
                    callback.onFail();
                    return;
                }
                UploadPicData rsp = response.result;
                callback.onSucc(rsp.picKey, rsp.picUrl);
            }
        };

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.e("load_image", "done");
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
                final byte[] rawData = bitmap2Byte(bitmap, 85);
                AsyncHelper.executeAsyncTask(new AsyncHelper.BDTask<UploadPicData>() {
                    @Override
                    public UploadPicData executeBackGround() {
                        try {
                            HttpContext2 context = new HttpContext2();
                            context.getRequest().setUrl(NetworkConfig.HOST + ADDRESS_UP_IMAGE);
                            context.getRequest().setMethod(HttpRequest2.HTTP_METHOD.POST);
                            HashMap<String, Object> mParams = new HashMap<String, Object>();
                            mParams.put("file", rawData);
                            List<Map.Entry<String, Object>> data = encodeInBackGround(new ArrayList<Map.Entry<String, Object>>(mParams.entrySet()));
                            context.getRequest().setPostData(data);
                            BdHttpManager2 httpCore2 = new BdHttpManager2(context);

                            httpCore2.post(2, 10000, 10000);

                            String errString = "";

                            if (context.getStatList().size() > 0) {
                                errString = context.getStatList().get(context.getStatList().size() - 1).exception;
                            }
                            if (context.getResponse().isNetOK()) {
                                HttpResponsedMessage responsedMessage = new HttpResponsedMessage();
                                responsedMessage.setStatusCode(context.getResponse().responseCode, errString);
                                responsedMessage.setHeader(context.getResponse().heads);
                                responsedMessage.setContentEncoding(context.getResponse().contentEncoding);
                                responsedMessage.setContentLength(context.getResponse().contentLength);
                                responsedMessage.setContentType(context.getResponse().contentType);
                                responsedMessage.decodeInBackGround(context.getResponse().retBytes);
                                String rsp = responsedMessage.mRet;
                                UploadPicData uploadData = ZXJsonUtil.fromJsonString(rsp, UploadPicData.class);

                                return uploadData;
                            } else {
                                return null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    public void postExecute(UploadPicData result) {
                        if (callback != null) {
                            if (result == null) {
                                callback.onFail();
                            } else {
                                callback.onSucc(result.picKey, result.picUrl);
                            }
                        }

                    }
                });
                ZXBHttpRequest<UploadPicData> request = new ZXBHttpRequest<UploadPicData>(UploadPicData.class, listener);
                request.addParams("file", bitmap2Byte(bitmap, 85));
                request.setMethod(Request.Method.POST);
                request.setPath(ADDRESS_UP_IMAGE);
                ZXBRequestManageer.sharedInstance().dealRequest(request);
                if (context instanceof ZXBaseActivity) {

                    ((ZXBaseActivity) context).sendRequest(request);
                } else {
                    request.send();
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("load_image", "fail");
                if (callback != null) {
                    callback.onFail();
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.e("load_image", "start");
                if (callback != null) {
                    callback.onPrepare();
                }
            }


        };
        if (imageView != null) {
            imageView.setTag(target);
        }
        Picasso.with(context)
                .load(uri).resize(IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)
                .into(target);

        return true;
    }


    public static List<Map.Entry<String, Object>> encodeInBackGround(List<Map.Entry<String, Object>> paramsList) {
        int size = paramsList.size();
        for (int i = 0; i < size; i++) {
            Map.Entry<String, Object> entry = paramsList.get(i);
            Object value = entry.getValue();
            if (value instanceof Integer || value instanceof Long) {
                entry.setValue(value.toString());
                continue;
            }
            if (!(value instanceof String) && !(value instanceof byte[])) {
                entry.setValue(getByte(value));
            }
        }
        return paramsList;
    }

    private static byte[] getByte(Object object) {
        byte[] data = null;
        try {

            Method method = object.getClass().getMethod("toByteArray");
            Object ob = method.invoke(object);
            if (ob != null && ob instanceof byte[]) {
                data = (byte[]) ob;
            }
        } catch (Exception e) {
            BdLog.e("getByte error");
        }
        return data;
    }


}

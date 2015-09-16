package com.zxzx74147.devlib.utils;

import com.zxzx74147.devlib.ZXApplicationDelegate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by zhengxin on 15/9/15.
 */
public class ZXAssetUtil {

    public static String getFromRaw(int id){
        try {
            InputStreamReader inputReader = new InputStreamReader(ZXApplicationDelegate.getApplication().getResources().openRawResource(id));
            BufferedReader bufReader = new BufferedReader(inputReader);
            return getFromBuffer(bufReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( ZXApplicationDelegate.getApplication().getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            return getFromBuffer(bufReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFromBuffer(BufferedReader bufReader){
        try {
            String line;
            StringBuffer sb  = new StringBuffer(1000);
            while((line = bufReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

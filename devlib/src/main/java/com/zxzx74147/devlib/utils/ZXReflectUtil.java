package com.zxzx74147.devlib.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by zhengxin on 15/9/15.
 */
public class ZXReflectUtil {

    public static void setBooleanValue(Object object ,String field,boolean value){
        try {
            Field fieldObj = object.getClass().getField(field);
            boolean access = fieldObj.isAccessible();
            if(!access) {
                fieldObj.setAccessible(true);
            }
            fieldObj.setBoolean(object,value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setStringValue(Object object ,String field,String value){
        try {
            Field fieldObj = object.getClass().getField(field);
            boolean access = fieldObj.isAccessible();
            if(!access) {
                fieldObj.setAccessible(true);
            }
            fieldObj.set(object, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setIntValue(Object object ,String field,int value){
        try {
            Field fieldObj = object.getClass().getField(field);
            boolean access = fieldObj.isAccessible();
            if(!access) {
                fieldObj.setAccessible(true);
            }
            fieldObj.setInt(object, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setFloatValue(Object object ,String field,float value){
        try {
            Field fieldObj = object.getClass().getField(field);
            boolean access = fieldObj.isAccessible();
            if(!access) {
                fieldObj.setAccessible(true);
            }
            fieldObj.setFloat(object, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void invokeFunction(Object object ,String function,List<Object> params,Class<?> ...types){
        try {
            Method method = object.getClass().getMethod(function,types);
            boolean access = method.isAccessible();
            if(!access) {
                method.setAccessible(true);
            }
            method.invoke(object,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

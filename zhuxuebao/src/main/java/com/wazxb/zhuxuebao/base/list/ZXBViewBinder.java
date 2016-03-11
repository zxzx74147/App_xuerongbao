package com.wazxb.zhuxuebao.base.list;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by zhengxin on 15/9/6.
 */
public abstract class ZXBViewBinder<T> {

    private HashMap<Integer,Class<? extends ZXBViewHolder>> mHolderTable = new HashMap<>();
    private HashMap<Integer,Class<? extends ViewDataBinding>> mViewTable = new HashMap<>();

    public void registeItem(int type,Class<? extends ViewDataBinding> mBinding,Class<? extends ZXBViewHolder> mClass){
        mHolderTable.put(type,mClass);
        mViewTable.put(type,mBinding);
    }

    public ZXBViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Class<? extends ZXBViewHolder> holderClass = mHolderTable.get(viewType);
        Class<? extends ViewDataBinding> bingClass = mViewTable.get(viewType);
        if(holderClass == null || bingClass == null){
            return null;
        }

        try {
            Method mothod = bingClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            ViewDataBinding binding = (ViewDataBinding) mothod.invoke(bingClass,LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
            Constructor constructor = holderClass.getConstructor(ViewDataBinding.class);
            ZXBViewHolder holder = (ZXBViewHolder) constructor.newInstance(binding);
            return holder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void onBindViewHolder(ZXBViewHolder<T> holder, T data){

        holder.bindData(data);
    }

    public abstract int getItemViewType(Object object);

}

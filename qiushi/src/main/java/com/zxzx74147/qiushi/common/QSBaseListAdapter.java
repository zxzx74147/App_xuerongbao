package com.zxzx74147.qiushi.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

/**
 * Created by zhengxin on 15/9/6.
 */
public class QSBaseListAdapter extends UltimateViewAdapter<QSViewHolder> {

    public static int VIEW_TYPE_BASE = UltimateViewAdapter.VIEW_TYPES.NORMAL+10;//用户空间偏移量
    private List<? extends Object> mData = null;
    private QSViewBinder mBinder;
    private Context mContext;


    public void setData(List<? extends Object> data){
        mData = data;
        notifyDataSetChanged();
    }

    public QSBaseListAdapter(Context context,QSViewBinder binder){
        mBinder = binder;
        mContext = context;
    }



    @Override
    public QSViewHolder getViewHolder(View view) {
        return new SimpleViewHolder(view);
    }

    @Override
    public QSViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        TextView text = new TextView(mContext);
        text.setText("abc");
        return new SimpleViewHolder(text);
//        return null;
    }

    @Override
    public void onBindViewHolder(QSViewHolder holder, int position) {
        Object itemData = getItem(position);
        if(itemData == null){
            return;
        }
        mBinder.onBindViewHolder(holder, itemData);
    }

    @Override
    public QSViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType) {
        if (viewType == VIEW_TYPES.FOOTER) {
            QSViewHolder viewHolder = getViewHolder(customLoadMoreView);
            if (getAdapterItemCount() == 0)
                viewHolder.itemView.setVisibility(View.GONE);
            return viewHolder;
        } else if (viewType == VIEW_TYPES.HEADER) {
            if (customHeaderView != null)
                return getViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.CHANGED_FOOTER) {
            QSViewHolder viewHolder = getViewHolder(customLoadMoreView);
            if (getAdapterItemCount() == 0)
                viewHolder.itemView.setVisibility(View.GONE);
            return viewHolder;
        }

        return mBinder.onCreateViewHolder(viewGroup,viewType-VIEW_TYPE_BASE);
    }

    @Override
    public int getAdapterItemCount() {
        return mData == null? 0:mData.size();
    }

    @Override
    public long generateHeaderId(int i) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemViewType(int position) {
        int type = super.getItemViewType(position);
        if(type != VIEW_TYPES.NORMAL){//Header or Footer
            return type;
        }
        type = mBinder.getItemViewType(getItem(position))+VIEW_TYPE_BASE;
        return type;
    }

    private Object getItem(int position){
        if(customHeaderView != null){//Normal item
            position--;
        }
        if(mData == null || position<0||position>=mData.size()){
            return null;
        }
        Object itemData = mData.get(position);
        return itemData;
    }

    private class SimpleViewHolder extends QSViewHolder{

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(Object data) {

        }
    }


}

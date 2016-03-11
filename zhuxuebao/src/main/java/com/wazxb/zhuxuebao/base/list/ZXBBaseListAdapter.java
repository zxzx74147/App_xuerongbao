package com.wazxb.zhuxuebao.base.list;

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
public class ZXBBaseListAdapter extends UltimateViewAdapter<ZXBViewHolder> {

    public static int VIEW_TYPE_BASE = VIEW_TYPES.NORMAL + 10;//用户空间偏移量
    private List mData = null;
    private ZXBViewBinder mBinder;
    private Context mContext;


    public void setData(List data) {
        mData = data;
        notifyDataSetChanged();
    }

    public ZXBBaseListAdapter(Context context, ZXBViewBinder binder) {
        mBinder = binder;
        mContext = context;
    }


    @Override
    public ZXBViewHolder getViewHolder(View view) {
        return new SimpleViewHolder(view);
    }

    @Override
    public ZXBViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        TextView text = new TextView(mContext);
        text.setText("abc");
        return new SimpleViewHolder(text);
    }

    @Override
    public void onBindViewHolder(ZXBViewHolder holder, int position) {
        Object itemData = getItem(position);
        if (itemData == null) {
            return;
        }
        mBinder.onBindViewHolder(holder, itemData);
    }

    @Override
    public ZXBViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPES.FOOTER) {
            ZXBViewHolder viewHolder = getViewHolder(customLoadMoreView);
            if (getAdapterItemCount() == 0)
                viewHolder.itemView.setVisibility(View.GONE);
            return viewHolder;
        } else if (viewType == VIEW_TYPES.HEADER) {
            if (customHeaderView != null)
                return getViewHolder(customHeaderView);
        }
//        else if (viewType == VIEW_TYPES.CHANGED_FOOTER) {
//            ZXBViewHolder viewHolder = getViewHolder(customLoadMoreView);
//            if (getAdapterItemCount() == 0)
//                viewHolder.itemView.setVisibility(View.GONE);
//            return viewHolder;
//        }

        return mBinder.onCreateViewHolder(viewGroup, viewType - VIEW_TYPE_BASE);
    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
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
        if (type != VIEW_TYPES.NORMAL) {//Header or Footer
            return type;
        }
        type = mBinder.getItemViewType(getItem(position)) + VIEW_TYPE_BASE;
        return type;
    }

    private Object getItem(int position) {
        if (customHeaderView != null) {//Normal item
            position--;
        }
        if (mData == null || position < 0 || position >= mData.size()) {
            return null;
        }
        Object itemData = mData.get(position);
        return itemData;
    }

    public void addData(List data) {
        if (data == null) {
            return;
        }
        if (mData == null) {
            setData(data);
            return;
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    private class SimpleViewHolder extends ZXBViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(Object data) {

        }
    }


}

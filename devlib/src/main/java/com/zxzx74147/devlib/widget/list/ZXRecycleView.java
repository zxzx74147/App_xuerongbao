package com.zxzx74147.devlib.widget.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton;
import com.zxzx74147.devlib.R;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zhengxin on 15/8/27.
 */
public class ZXRecycleView extends UltimateRecyclerView {

    public PtrFrameLayout mPtrFrameLayout;

    public ZXRecycleView(Context context) {
        super(context);
    }
    public ZXRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ZXRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initViews() {
        //super.initViews();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.zx_recycle_view, this);
        mPtrFrameLayout = (PtrFrameLayout)this.findViewById(R.id.store_house_ptr_frame);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ultimate_list);
        mSwipeRefreshLayout = null;

        if (mRecyclerView != null) {

            mRecyclerView.setClipToPadding(mClipToPadding);
            if (mPadding != -1.1f) {
                mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }
        }

        defaultFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.defaultFloatingActionButton);
        setDefaultScrollListener();

        mEmpty = (ViewStub) view.findViewById(R.id.emptyview);
        mFloatingButtonViewStub = (ViewStub) view.findViewById(R.id.floatingActionViewStub);

        mEmpty.setLayoutResource(mEmptyId);

        mFloatingButtonViewStub.setLayoutResource(mFloatingButtonId);

        if (mEmptyId != 0)
            mEmptyView = mEmpty.inflate();
        mEmpty.setVisibility(View.GONE);

        if (mFloatingButtonId != 0) {
            mFloatingButtonView = mFloatingButtonViewStub.inflate();
            mFloatingButtonView.setVisibility(View.VISIBLE);
        }

    }

//    public void setCustomSwipeToRefresh() {
//        this.mPtrFrameLayout = (PtrFrameLayout)this.findViewById(R.id.store_house_ptr_frame);
//        this.mPtrFrameLayout.setResistance(1.7F);
//        this.mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2F);
//        this.mPtrFrameLayout.setDurationToClose(200);
//        this.mPtrFrameLayout.setDurationToCloseHeader(1000);
//        this.mPtrFrameLayout.setPullToRefresh(false);
//        this.mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
//    }

}

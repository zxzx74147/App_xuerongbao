package com.zxzx74147.devlib.widget.list;

import android.content.Context;
import android.util.AttributeSet;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

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

//    @Override
//    protected void initViews() {
//        //super.initViews();
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.zx_recycle_view, this);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.ultimate_list);
//        mSwipeRefreshLayout = null;
//
//        if (mRecyclerView != null) {
//
//            mRecyclerView.setClipToPadding(mClipToPadding);
//            if (mPadding != -1.1f) {
//                mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
//            } else {
//                mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
//            }
//        }
//
//        defaultFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.defaultFloatingActionButton);
//        setDefaultScrollListener();
//
//        mEmpty = (ViewStub) view.findViewById(R.id.emptyview);
//        mFloatingButtonViewStub = (ViewStub) view.findViewById(R.id.floatingActionViewStub);
//
//        mEmpty.setLayoutResource(mEmptyId);
//
//        mFloatingButtonViewStub.setLayoutResource(mFloatingButtonId);
//
//        if (mEmptyId != 0)
//            mEmptyView = mEmpty.inflate();
//        mEmpty.setVisibility(View.GONE);
//
//        if (mFloatingButtonId != 0) {
//            mFloatingButtonView = mFloatingButtonViewStub.inflate();
//            mFloatingButtonView.setVisibility(View.VISIBLE);
//        }
//
//    }
//
//    public void setCustomSwipeToRefresh() {
//        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
//        mPtrFrameLayout.setResistance(1.7f);
//        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
//        mPtrFrameLayout.setDurationToClose(200);
//        mPtrFrameLayout.setDurationToCloseHeader(1000);
//        mPtrFrameLayout.setPullToRefresh(true);
//        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
//    }
}

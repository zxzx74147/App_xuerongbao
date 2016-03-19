package com.wazxb.xuerongbao.base.list;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.zxzx74147.devlib.widget.list.ZXRecycleView;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by zhengxin on 15/9/6.
 */
public abstract class ZXBBaseListActivity extends ZXBBaseActivity {
    private ZXBBaseListAdapter mAdapter;
    private ZXRecycleView mRecycleView;
    protected ViewDataBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutID() == 0 ? R.layout.qs_base_list_activity : getLayoutID();
        mBinding = DataBindingUtil.setContentView(this, layoutId);

        mRecycleView = (ZXRecycleView) findViewById(R.id.list);

        mAdapter = new ZXBBaseListAdapter(this, getItemBinder());
        mRecycleView.setAdapter(mAdapter);

        mRecycleView.setLayoutManager(getLayoutManager());

        mRecycleView.setOnLoadMoreListener(mLoadMoreListener);


        refreshingString();
    }

    protected ZXRecycleView getRecyclerView() {
        return mRecycleView;
    }


    protected int getLayoutID() {
        return 0;
    }

    protected abstract ZXBViewBinder getItemBinder();

    protected RecyclerView.LayoutManager getLayoutManager() {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        return lm;
    }

    private ZXRecycleView.OnLoadMoreListener mLoadMoreListener = new ZXRecycleView.OnLoadMoreListener() {
        @Override
        public void loadMore(int i, int i1) {
            loadMoreData();
        }
    };

    protected abstract void refreshData();

    protected void loadMoreData() {

    }

    protected void setData(List<? extends Object> mData) {

        mRecycleView.mPtrFrameLayout.refreshComplete();
        mAdapter.setData(mData);
        if (mData == null | mData.size() == 0) {
            showEmptyView();
        } else {
            hideEmptyView();
        }
        hideProgressBar();
    }

    protected void completeLoading() {
        mRecycleView.mPtrFrameLayout.refreshComplete();
    }

    protected void addData(List<? extends Object> mData) {
        hideProgressBar();
        mRecycleView.mPtrFrameLayout.refreshComplete();
        mAdapter.addData(mData);
    }

    private void refreshingString() {
        showProgressBar();
        MaterialHeader materialHeader = new MaterialHeader(this);

        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(this);

        storeHouseHeader.initWithString("zxb");
        PullView pullView = new PullView(this);
//        mRecycleView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
//        mRecycleView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        mRecycleView.mPtrFrameLayout.setHeaderView(pullView);
        mRecycleView.mPtrFrameLayout.addPtrUIHandler(pullView);
        mRecycleView.mPtrFrameLayout.autoRefresh(false);
        mRecycleView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                return canbePullDown;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                refreshData();
            }
        });

    }
}

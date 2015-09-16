package com.zxzx74147.qiushi.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zxzx74147.devlib.base.ZXBaseActivity;
import com.zxzx74147.devlib.widget.list.ZXRecycleView;
import com.zxzx74147.qiushi.R;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by zhengxin on 15/9/6.
 */
public abstract class QSBaseListActivity extends ZXBaseActivity {
    private QSBaseListAdapter mAdapter;
    private ZXRecycleView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID() == 0 ? R.layout.qs_base_list_activity : getLayoutID());
        mRecycleView = (ZXRecycleView) findViewById(R.id.list);

        mAdapter = new QSBaseListAdapter(this,getItemBinder());
        mRecycleView.setAdapter(mAdapter);

        mRecycleView.setLayoutManager(getLayoutManager());

        mRecycleView.setOnLoadMoreListener(mLoadMoreListener);

//        mRecycleView.enableLoadmore();
        refreshingString();
    }

    protected ZXRecycleView getRecyclerView(){
        return mRecycleView;
    }


    protected int getLayoutID(){
        return 0;
    }

    protected abstract QSViewBinder getItemBinder();

    protected RecyclerView.LayoutManager getLayoutManager(){
        RecyclerView.LayoutManager lm  = new LinearLayoutManager(this);
        return lm;
    }

    private ZXRecycleView.OnLoadMoreListener mLoadMoreListener = new ZXRecycleView.OnLoadMoreListener() {
        @Override
        public void loadMore(int i, int i1) {

        }
    };

    protected abstract void refreshData();

    protected void setData(List<? extends Object> mData){
        mRecycleView.mPtrFrameLayout.refreshComplete();
        mAdapter.setData(mData);
    }

    private void refreshingString() {
        MaterialHeader materialHeader = new MaterialHeader(this);

        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(this);

        storeHouseHeader.initWithString("amazon");
        mRecycleView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        mRecycleView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
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

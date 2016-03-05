package com.zxzx74147.devlib.widget;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.base.ZXBaseActivity;
import com.zxzx74147.devlib.utils.CustomToast;

/**
 * @author Simon
 */
public abstract class BaseFragment extends Fragment implements OnClickListener,
        OnLongClickListener, OnItemClickListener, OnItemLongClickListener,
        DialogInterface.OnClickListener, OnScrollListener {

    public final int STATE_BACKGTOUND = 0;
    public final int STATE_FRONT = 1;

    private int mState = STATE_BACKGTOUND;
    private boolean mNeedRefresh = false;

    protected long mLastRefreshTime = System.currentTimeMillis();
    private String mStatisticsKey;

    private boolean isPrimary;
    protected ZXBaseActivity mActivity;
    private CustomToast customToast;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (ZXBaseActivity) activity;
        customToast = CustomToast.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStatisticsKey = getStatisicsKey();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showToast(String str) {
        if (getActivity() == null) {
            return;
        }
        customToast.showToast(str, 1000);
    }

    public void showToast(int resourceId) {
        if (getActivity() == null) {
            return;
        }
        customToast.showToast(resourceId, 1000);
    }


    /**
     * 显示提示信息
     *
     * @param str      需要提示的字符串
     * @param autoHide 离开当前屏幕时，是否自动隐藏
     */
    public void showToast(String str, boolean autoHide) {
        customToast.showToast(str, 2000);

    }

    /**
     * 显示提示信息
     *
     * @param autoHide 离开当前屏幕时，是否自动隐藏
     */
    public void showToast(int resourceId, boolean autoHide) {
        customToast.showToast(ZXApplicationDelegate.getApplication().getResources().getString(resourceId), 2000);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mState = STATE_FRONT;


        if (!isShow()) {
            return;
        }

        if (isPrimary) {
            onPrimary();
        }

        if (mNeedRefresh) {
            mNeedRefresh = false;
            clearAndRefresh();
        }

    }

    @Override
    public void onPause() {
        mState = STATE_BACKGTOUND;

        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onChangeSkinType(int skinType) {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onResume();
        } else {
            onPause();
        }
    }

    protected boolean isShow() {
        if (isHidden()) {
            return false;
        }

        return true;
    }


    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
        if (isResumed()) {
            onPrimary();
        }
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    /**
     * Usage:FragmentTabHost中fragment切换时调用，友盟统计，统计页面点击次数
     */
    public void onShow(boolean hasTip) {

    }

    //当fragment在viewpager中选中状态下再次点击相应选择按钮出发
    public void onDupSelected() {

    }

    public void onPrimary() {

    }

    protected String getStatisicsKey() {
        return null;
    }

    // 清空并刷新页面
    public void clearAndRefresh() {
        setRefreshTime();
    }

    public void refresh() {
        setRefreshTime();
    }

    // 清空并刷新页面
    protected void setRefreshTime() {
        mLastRefreshTime = System.currentTimeMillis();
    }


}

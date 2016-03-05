package com.wazxb.zhuxuebao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityRegitsterBinding;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.UidData;
import com.zxzx74147.devlib.widget.BaseFragment;
import com.zxzx74147.devlib.widget.tabhost.CommonFragmentTabIndicator;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabSpec;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabStructure;

public class RegitsterActivity extends ZXBBaseActivity {
    ActivityRegitsterBinding mBinding = null;
    private BaseFragment[] mFragmentTable = new BaseFragment[2];
    ZXBHttpRequest<UidData> mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_regitster);
        addTabs();
    }

    private void setTab(int index) {
        mBinding.titleBar.setText(getResources().getString(R.string.register) + "(" + (index + 1) + "/2)");
        mBinding.tabHost.setCurrentIndex(index);
        mBinding.titleBar.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mBinding.tabHost.getCurrentTabIndex();
                if (index <= 0) {
                    finish();
                } else {
                    mBinding.tabHost.setCurrentIndex(index - 1);
                }
            }
        });

        mBinding.titleBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mBinding.tabHost.getCurrentTabIndex();
                if (((RegisterFragment1) mFragmentTable[index]).checkDone()) {
                    mBinding.tabHost.setCurrentIndex(index + 1);
                }
            }
        });

        mBinding.tabHost.setOnScrollChangedListener(mOnPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mBinding.titleBar.setText(getResources().getString(R.string.register) + "(" + (position + 1) + "/2)");
            if (position == 1) {
                mBinding.titleBar.hideRight();
            } else {
                mBinding.titleBar.showRight();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void addTabs() {
        mBinding.tabHost.setup(this, getSupportFragmentManager());
        {
            FragmentTabStructure structure = new FragmentTabStructure();
            structure.frag = new RegisterFragment1();
            createAndAddTabSpec(structure);
            mFragmentTable[0] = structure.frag;
        }


        {
            FragmentTabStructure structure = new FragmentTabStructure();
            structure.frag = new RegisterFragment4();
            createAndAddTabSpec(structure);
            mFragmentTable[1] = structure.frag;
        }
        mBinding.tabHost.initViewPager();
        setTab(0);
    }

    private void createAndAddTabSpec(FragmentTabStructure fragTab) {
        if (fragTab == null) {
            return;
        }
        FragmentTabSpec tab = new FragmentTabSpec();
        tab.mFragment = fragTab.frag;
        tab.mType = fragTab.type;
        CommonFragmentTabIndicator mInner = new CommonFragmentTabIndicator(RegitsterActivity.this);
        mInner.setPadding(0, getResources().getDimensionPixelSize(R.dimen.default_gap_7), 0, 0);
        mInner.setContentView(new View(RegitsterActivity.this));
        tab.mWidget = mInner;

        mBinding.tabHost.addTabSpec(tab);
    }




}

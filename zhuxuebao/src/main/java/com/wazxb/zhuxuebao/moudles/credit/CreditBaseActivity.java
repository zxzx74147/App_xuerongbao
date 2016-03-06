package com.wazxb.zhuxuebao.moudles.credit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityCreditBaseBinding;
import com.wazxb.zhuxuebao.moudles.account.AccountManager;
import com.wazxb.zhuxuebao.network.NetworkConfig;
import com.wazxb.zhuxuebao.network.ZXBHttpRequest;
import com.wazxb.zhuxuebao.storage.data.UserAllData;
import com.wazxb.zhuxuebao.util.FillRqeustUtil;
import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.devlib.widget.BaseFragment;
import com.zxzx74147.devlib.widget.tabhost.CommonFragmentTabIndicator;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabSpec;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabStructure;

public class CreditBaseActivity extends ZXBBaseActivity {
    ActivityCreditBaseBinding mBinding = null;
    private BaseFragment[] mFragmentTable = new BaseFragment[3];
    ZXBHttpRequest<UserAllData> mRequest = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit_base);
        addTabs();
    }

    private void setTab(int index) {
        mBinding.titleBar.setText(getResources().getString(R.string.credit_base) + "(" + (index + 1) + "/3)");
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
                switch (index) {
                    case 0:
                        if (((CreditBaseFragment1) mFragmentTable[0]).checkDone()) {
                            mBinding.tabHost.setCurrentIndex(index + 1);
                        }
                        break;
                    case 1:
                        if (((CreditBaseFragment2) mFragmentTable[1]).checkDone()) {
                            mBinding.tabHost.setCurrentIndex(index + 1);
                        }
                        break;
                    case 2:
                        submit();
                        break;
                }
            }
        });

        mBinding.tabHost.setOnScrollChangedListener(mOnPageChangeListener);
    }

    private void submit() {
        if (mRequest != null) {
            mRequest.cancel();
            mRequest = null;
        }
        mRequest = new ZXBHttpRequest<>(UserAllData.class, new HttpResponseListener<UserAllData>() {
            @Override
            public void onResponse(HttpResponse<UserAllData> response) {
                mRequest = null;
                if (response.hasError()) {
                    showToast(response.errorString);
                    return;
                }
                AccountManager.sharedInstance().setUserAllData(response.result);
                finish();
            }
        });
        mRequest.setPath(NetworkConfig.ADDRESS_CD_BASE);
        FillRqeustUtil.fillRequest(mRequest, getWindow().getDecorView());
        sendRequest(mRequest);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mBinding.titleBar.setText(getResources().getString(R.string.credit_base) + "(" + (position + 1) + "/3)");
            if (position == 2) {
                mBinding.titleBar.setRightText(R.string.done);
            } else {
                mBinding.titleBar.setRightText(R.string.next_step);
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
            structure.frag = new CreditBaseFragment1();
            createAndAddTabSpec(structure);
            mFragmentTable[0] = structure.frag;

        }


        {
            FragmentTabStructure structure = new FragmentTabStructure();
            structure.frag = new CreditBaseFragment2();
            createAndAddTabSpec(structure);
            mFragmentTable[1] = structure.frag;
        }


        {
            FragmentTabStructure structure = new FragmentTabStructure();
            structure.frag = new CreditBaseFragment3();
            createAndAddTabSpec(structure);
            mFragmentTable[2] = structure.frag;
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
        CommonFragmentTabIndicator mInner = new CommonFragmentTabIndicator(CreditBaseActivity.this);
        mInner.setPadding(0, getResources().getDimensionPixelSize(R.dimen.default_gap_7), 0, 0);
        mInner.setContentView(new View(CreditBaseActivity.this));
        tab.mWidget = mInner;
        mBinding.tabHost.addTabSpec(tab);
    }


}

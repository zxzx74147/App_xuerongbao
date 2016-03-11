package com.wazxb.zhuxuebao.moudles.borrow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.base.ZXBBaseActivity;
import com.wazxb.zhuxuebao.databinding.ActivityBorrowBinding;
import com.zxzx74147.devlib.widget.tabhost.CommonFragmentTabIndicator;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabSpec;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabStructure;

/**
 * Created by zhengxin on 16/3/8.
 */
public class BorrowActivity extends ZXBBaseActivity {

    private ActivityBorrowBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_borrow);
        addTabs();
    }

    private void addTabs() {
        mBinding.tabHost.setup(this, getSupportFragmentManager());
        {
            FragmentTabStructure structure = new FragmentTabStructure();
            CommonBorrowFragment fragment = new CommonBorrowFragment();
            fragment.setMode(BorrowConfig.BORROW_FULI);
            structure.frag = fragment;
            structure.textResId = R.string.borrow_fuli;
            createAndAddTabSpec(structure);
        }

        {
            FragmentTabStructure structure = new FragmentTabStructure();
            CommonBorrowFragment fragment = new CommonBorrowFragment();
            fragment.setMode(BorrowConfig.BORROW_HUOLI);
            structure.frag = fragment;
            structure.textResId = R.string.borrow_huoli;
            createAndAddTabSpec(structure);
        }

        {
            FragmentTabStructure structure = new FragmentTabStructure();
            CommonBorrowFragment fragment = new CommonBorrowFragment();
            fragment.setMode(BorrowConfig.BORROW_YUELI);
            structure.frag = fragment;
            structure.textResId = R.string.borrow_yueli;
            createAndAddTabSpec(structure);
        }
        mBinding.tabHost.initViewPager();
    }

    private void createAndAddTabSpec(FragmentTabStructure fragTab) {
        if (fragTab == null) {
            return;
        }
        FragmentTabSpec tab = new FragmentTabSpec();
        tab.mFragment = fragTab.frag;
        tab.mType = fragTab.type;
        TextView mTextView = new TextView(this);
        mTextView.setText(fragTab.textResId);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
                .getDimension(R.dimen.default_size_29));
        mTextView.setPadding(getResources().getDimensionPixelSize(R.dimen.default_gap_13),
                0, getResources().getDimensionPixelSize(R.dimen.default_gap_13), 0);
        mTextView.setTextColor(getResources().getColorStateList(R.color.tab_text_color));
        mTextView.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.default_gap_3));
        mTextView.setGravity(Gravity.CENTER);
        CommonFragmentTabIndicator mInner = new CommonFragmentTabIndicator(this);
        mInner.setPadding(0, getResources().getDimensionPixelSize(R.dimen.default_gap_7), 0, 0);
        mInner.setContentView(mTextView);
        tab.mWidget = mInner;
        mBinding.tabHost.addTabSpec(tab);
    }
}

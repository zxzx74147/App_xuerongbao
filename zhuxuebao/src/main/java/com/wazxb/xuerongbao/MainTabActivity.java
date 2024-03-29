package com.wazxb.xuerongbao;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityMainTabBinding;
import com.wazxb.xuerongbao.moudles.LogoActivity;
import com.wazxb.xuerongbao.moudles.account.InitActivity;
import com.wazxb.xuerongbao.moudles.home.HomeDelegate;
import com.wazxb.xuerongbao.moudles.more.MoreDelegate;
import com.wazxb.xuerongbao.moudles.personal.PersonalDelegate;
import com.zxzx74147.devlib.utils.SharedPreferenceHelper;
import com.zxzx74147.devlib.utils.ZXActivityJumpHelper;
import com.zxzx74147.devlib.widget.tabhost.CommonFragmentTabIndicator;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabSpec;
import com.zxzx74147.devlib.widget.tabhost.FragmentTabStructure;

public class MainTabActivity extends ZXBBaseActivity {

    private ActivityMainTabBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_tab);
        addTabs();

        boolean isFisrt = SharedPreferenceHelper.getBoolean("is_first", true);
        if (isFisrt) {
            ZXActivityJumpHelper.startActivity(this, InitActivity.class);
        } else {
            ZXActivityJumpHelper.startActivity(this, LogoActivity.class);
        }
        SharedPreferenceHelper.saveBoolean("is_first", false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void addTabs() {
        mBinding.tabHost.setup(this, getSupportFragmentManager());
        FragmentTabStructure home = HomeDelegate.newInstance();
        createAndAddTabSpec(home);

        FragmentTabStructure personal = PersonalDelegate.newInstance();
        createAndAddTabSpec(personal);

        FragmentTabStructure more = MoreDelegate.newInstance();
        createAndAddTabSpec(more);
        mBinding.tabHost.initViewPager();
    }

    private void createAndAddTabSpec(FragmentTabStructure fragTab) {
        if (fragTab == null) {
            return;
        }
        FragmentTabSpec tab = new FragmentTabSpec();
        tab.mFragment = fragTab.frag;
        tab.mType = fragTab.type;
        TextView mTextView = new TextView(MainTabActivity.this);
        mTextView.setText(fragTab.textResId);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
                .getDimension(R.dimen.default_size_24));
        mTextView.setPadding(getResources().getDimensionPixelSize(R.dimen.default_gap_13),
                0, getResources().getDimensionPixelSize(R.dimen.default_gap_13), 0);
        mTextView.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.default_gap_3));
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(0, fragTab.textDrawable, 0, 0);
        CommonFragmentTabIndicator mInner = new CommonFragmentTabIndicator(MainTabActivity.this);
        mTextView.setTextColor(getResources().getColorStateList(R.color.tab_text_color));
        mInner.setPadding(0, getResources().getDimensionPixelSize(R.dimen.default_gap_7), 0, 0);
        mInner.setContentView(mTextView);
        tab.mWidget = mInner;

        mBinding.tabHost.addTabSpec(tab);
    }
}

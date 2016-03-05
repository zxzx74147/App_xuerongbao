/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zxzx74147.devlib.widget.tabhost;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zxzx74147.devlib.R;
import com.zxzx74147.devlib.widget.FixedSpeedScroller;
import com.zxzx74147.devlib.widget.ScrollControlViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Version of {@link android.support.v4.app.FragmentTabHost} that can be
 * used with the platform {@link android.app.Fragment} APIs.  You will not
 * normally use this, instead using action bar tabs.
 */
public class FragmentTabHost extends LinearLayout {


    private Context mContext;
    private FragmentManager mFragmentManager;

    private ViewPager.OnPageChangeListener mOutterOnScrollListener;
    private boolean mAttached;

    private ScrollControlViewPager mViewPager;
    private FragmentTabWidget mTabWidgetView;
    private FragmentAdapter mPagerAdapter;
    private int mTabMode = TabConfig.MODE_TOP;
    private int mLastTabIndex = -1;
    private FragmentTabSpec mCurentTabSpec;
    private final List<FragmentTabSpec> mTabSpecs = new ArrayList<FragmentTabSpec>();
    private boolean mScrollable = true;
    private boolean mSmoothScroll = false;
    private int mDefaultSelect = 0;
    private int mTabHeight;

    public FragmentTabWidget getTabWidget() {
        return mTabWidgetView;
    }

    public void setCurrentIndex(int index) {
        mViewPager.setCurrentItem(index,mSmoothScroll);
        mTabWidgetView.setCurrentTab(index, true);
    }

    public void setCurrentTabByType(int type) {
        for (int i = 0; i < mTabSpecs.size(); i++) {
            if (mTabSpecs.get(i).mType == type) {
                setCurrentIndex(i);
                return;
            }
        }
    }

    public void setSmoothScroll(boolean isScroll){
        mViewPager.setScrollable(isScroll);
    }

    public FragmentTabSpec getTabByType(int type) {
        for (int i = 0; i < mTabSpecs.size(); i++) {
            if (mTabSpecs.get(i).mType == type) {
                return mTabSpecs.get(i);
            }
        }
        return null;
    }

    public FragmentTabSpec getTabByIndex(int index) {
        return mTabSpecs.get(index);
    }

    public void setSelectedTag(int i) {
        mTabWidgetView.setCurrentTab(i, false);
    }

    public void dismissNotify(int type) {
        FragmentTabSpec spec = getTabByType(type);
        if (spec != null && spec.mWidget != null && spec.mWidget instanceof IFragmentTabIndicator) {
            ((IFragmentTabIndicator) spec.mWidget).dismissTip();
        }
    }

    public void showNotify(int type, int count) {
        FragmentTabSpec spec = getTabByType(type);
        if (spec != null && spec.mWidget != null && spec.mWidget instanceof IFragmentTabIndicator) {
            ((IFragmentTabIndicator) spec.mWidget).showTip(count);
        }
    }

    public void showNotify(int type) {
        FragmentTabSpec spec = getTabByType(type);
        if (spec != null && spec.mWidget != null && spec.mWidget instanceof IFragmentTabIndicator) {
            ((IFragmentTabIndicator) spec.mWidget).showTip();
        }
    }

    public int getCurrentTabType() {
        if (mCurentTabSpec == null) {
            return 0;
        }
        return mCurentTabSpec.mType;
    }

    public int getCurrentTabIndex() {
        return mViewPager.getCurrentItem();
    }


    public FragmentTabHost(Context context) {
        // Note that we call through to the version that takes an AttributeSet,
        // because the simple Context construct can result in a broken object!
        super(context, null);
        mContext = context;
        initInner(null);
    }

    public FragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initInner(attrs);
    }


    public void setup(Context context, FragmentManager manager) {

        mContext = context;
        mFragmentManager = manager;
    }

    public void setOnScrollChangedListener(ViewPager.OnPageChangeListener l) {
        mOutterOnScrollListener = l;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // We are now ready to go.  Make sure we are switched to the
        // correct tab.
        mAttached = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAttached = false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }


    private void initInner(AttributeSet attrs) {
        setOrientation(VERTICAL);
        LayoutInflater mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (attrs != null) {
            TypedArray attsArray = mContext.obtainStyledAttributes(attrs,
                    R.styleable.FragmentTabHost);
            mTabMode = attsArray.getInt(R.styleable.FragmentTabHost_tabPosition, TabConfig.MODE_TOP);
            mSmoothScroll = attsArray.getBoolean(R.styleable.FragmentTabHost_smoothScroll, false);
            mScrollable = attsArray.getBoolean(R.styleable.FragmentTabHost_scrollable, true);
            mDefaultSelect = attsArray.getInt(R.styleable.FragmentTabHost_defaultSelect, 0);
            mTabHeight = (int) attsArray.getDimension(R.styleable.FragmentTabHost_tabHeight, mTabHeight);

            attsArray.recycle();
        }

        if (mTabMode == TabConfig.MODE_TOP) {
            mInflater.inflate(R.layout.fragment_tabhost_new_2, this, true);
        } else {
            mInflater.inflate(R.layout.fragment_tabhost_new, this, true);
        }
        mTabWidgetView = (FragmentTabWidget) findViewById(R.id.tabcontainer);
        mViewPager = (ScrollControlViewPager) findViewById(R.id.viewpager);
        if (attrs != null) {
            TypedArray attsArray = mContext.obtainStyledAttributes(attrs,
                    R.styleable.FragmentTabHost);
            mTabWidgetView.applayAttr(attsArray);
            attsArray.recycle();
        }

        mCurentTabSpec = null;
        mTabWidgetView.getLayoutParams().height = mTabHeight;
        mTabWidgetView.setTabSelectionListener(mOnTabSelectionChanged);
    }

    public void initViewPager() {
        mViewPager.setScrollable(mScrollable);
        mViewPager.setOffscreenPageLimit(mTabSpecs.size() - 1);
        mViewPager.setOnPageChangeListener(mInnerOnPageChangeListener);
        mPagerAdapter = new FragmentAdapter(mFragmentManager, mTabSpecs);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mDefaultSelect, mSmoothScroll);
        mTabWidgetView.setCurrentTab(mDefaultSelect, true);
        mViewPager.setOffscreenPageLimit(mTabSpecs.size() - 1);
        mPagerAdapter.notifyDataSetChanged();
        setViewPagerScrollSpeed();
    }

    public void setDefaultSelect(int defaultSelect){
        mDefaultSelect = defaultSelect;
    }

    public class FragmentAdapter extends android.support.v4.app.FragmentPagerAdapter {

        private int mPrimaryPosition = -1;

        private List<FragmentTabSpec> specs;

        public FragmentAdapter(FragmentManager fm, List<FragmentTabSpec> specs) {
            super(fm);
            this.specs = specs;
        }

        @Override
        public Fragment getItem(int index) {
            return specs.get(index).mFragment;
        }

        @Override
        public long getItemId(int position) {
            return specs.get(position).mFragment.hashCode();
        }

        @Override
        public int getCount() {
            return specs.size();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position,
                                   Object object) {
            super.setPrimaryItem(container, position, object);

            if (mPrimaryPosition != position) {
                if(mCurentTabSpec!=null){
                    mCurentTabSpec.mFragment.setPrimary(false);
                }
                mCurentTabSpec = specs.get(position);
                mCurentTabSpec.mFragment.setPrimary(true);
                mPrimaryPosition = position;
            }
        }
    }

    private ViewPager.OnPageChangeListener mInnerOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mTabWidgetView.changeLeft(position, positionOffset);
            if (mOutterOnScrollListener != null) {
                mOutterOnScrollListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            mTabWidgetView.setCurrentTab(position, true);
            if (mOutterOnScrollListener != null) {
                mOutterOnScrollListener.onPageSelected(position);
            }
            mLastTabIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOutterOnScrollListener != null) {
                mOutterOnScrollListener.onPageScrollStateChanged(state);
            }
        }
    };

    public void addTabSpec(FragmentTabSpec spec) {
        addTabSpec(spec, -1);
    }

    public void addTabSpec(FragmentTabSpec tabSpec, int index) {

        if (tabSpec.mFragment == null) {
            throw new IllegalArgumentException(
                    "you must create the tab content");
        }

        if (mTabSpecs.contains(tabSpec)) {
            return;
        }
        mTabWidgetView.addView(tabSpec.mWidget, index);
        if (index == -1) {
            mTabSpecs.add(tabSpec);
        } else {
            mTabSpecs.add(index, tabSpec);
        }

        if (mPagerAdapter != null) {
            mPagerAdapter.notifyDataSetChanged();
        }
        if (mCurentTabSpec == null) {
            mCurentTabSpec = tabSpec;
        }
    }

    private FragmentTabWidget.OnTabSelectionChanged mOnTabSelectionChanged = new FragmentTabWidget.OnTabSelectionChanged() {
        @Override
        public void onTabSelectionChanged(int tabIndex, boolean clicked) {
            if (mLastTabIndex == tabIndex) {
                FragmentTabSpec spec = getFragmentByIndex(tabIndex);
                if (spec.mFragment != null) {
                    spec.mFragment.onDupSelected();
                }
            } else {
                FragmentTabSpec spec = getFragmentByIndex(tabIndex);
                if (spec.mFragment != null) {
                    boolean hasTip = false;
                    if (spec.mWidget instanceof IFragmentTabIndicator) {
                        hasTip = ((IFragmentTabIndicator) spec.mWidget).hasTip();
                    }
                    spec.mFragment.onShow(hasTip);
                }
            }
            mLastTabIndex = tabIndex;

            if (mViewPager != null) {
                setCurrentIndex(tabIndex);
            }

        }
    };

    private FragmentTabSpec getFragmentByIndex(int index) {
        if (index < 0 || index >= mTabSpecs.size()) {
            return null;
        }
        return mTabSpecs.get(index);

    }

    public FragmentTabSpec getCurrentSpec() {
        return mCurentTabSpec;
    }

    public void onDupSelected() {
        if (mCurentTabSpec != null) {
            mCurentTabSpec.mFragment.onDupSelected();
        }
    }

    private void setViewPagerScrollSpeed() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext());
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

}

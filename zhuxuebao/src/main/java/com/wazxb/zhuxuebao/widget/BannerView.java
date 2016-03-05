package com.wazxb.zhuxuebao.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.storage.data.CarouselData;
import com.wazxb.zhuxuebao.util.ImageUtil;
import com.zxzx74147.devlib.base.BaseView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhengxin on 16/3/1.
 */
public class BannerView extends BaseView<List<CarouselData>> {

    private static final int INV = 10 * 1000;
    private ViewPager mViewPager = null;
    private int mCurrentIndex = INDEX_OFFSET;
    private static final int INDEX_OFFSET = 100;
    private List<CarouselData> mList = new LinkedList<>();
    private BannerViewPagerAdapter mAdapter = new BannerViewPagerAdapter();
    private LinearLayout mInd = null;
    private int size = 0;

    public BannerView(Context context) {
        super(context, R.layout.banner_layout);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.view_pager);
        mInd = (LinearLayout) mRootView.findViewById(R.id.radio);
//        mViewPager.setOnTouchListener(mOnTouchListener);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mViewPager.setAdapter(mAdapter);
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mViewPager.removeCallbacks(mNextRunnable);
            mViewPager.postDelayed(mNextRunnable, INV);
            int num = mInd.getChildCount();
            if (size == 0) {
                return;
            }
            position %= size;
            for (int i = 0; i < num; i++) {
                View v = mInd.getChildAt(i);
                if (i == position) {
                    v.setBackgroundResource(R.drawable.ind_gray);
                } else {
                    v.setBackgroundResource(R.drawable.ind_white);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mViewPager.removeCallbacks(mNextRunnable);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    mViewPager.removeCallbacks(mNextRunnable);
                    mViewPager.postDelayed(mNextRunnable, INV);
                    break;
            }
            return true;
        }
    };

    public void clear() {
        mList.clear();
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    public void setData(List<CarouselData> data) {
        super.setData(data);
        clear();
        if (data == null || data.size() == 0) {
            return;
        }
        mList = data;
        mViewPager.getAdapter().notifyDataSetChanged();
        mViewPager.removeCallbacks(mNextRunnable);
        mViewPager.postDelayed(mNextRunnable, INV);
        mViewPager.setCurrentItem(mCurrentIndex, false);
        if (size == data.size()) {
            return;
        }
        if (size < data.size()) {
            for (int i = 0; i < data.size() - size; i++) {
                View view = new View(mContext);
                view.setBackgroundResource(R.drawable.ind_white);
                int width = mContext.getResources().getDimensionPixelSize(R.dimen.default_gap_7);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
                if (mInd.getChildCount() > 0) {
                    lp.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.default_gap_10);
                }
                mInd.addView(view, lp);
            }
            size = data.size();
        } else {
            for (int i = 0; i < size - data.size(); i++) {
                if (mInd.getChildCount() == 0) {
                    break;

                }
                mInd.removeViewAt(mInd.getChildCount() - 1);
            }
        }
        size = data.size();
    }

    private Runnable mNextRunnable = new Runnable() {
        @Override
        public void run() {
            if (mList.size() > 1) {
                mCurrentIndex++;
                mViewPager.setCurrentItem(mCurrentIndex, true);
                mViewPager.removeCallbacks(mNextRunnable);
                mViewPager.postDelayed(mNextRunnable, INV);
            }
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CarouselData ad = (CarouselData) v.getTag();
            //TODO
        }
    };

    private class BannerViewPagerAdapter extends PagerAdapter {

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 1000;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            container.addView(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(mOnClickListener);
            if (mList.size() == 0) {
                return imageView;
            }
            int index_t = position % mList.size();
            CarouselData ad = mList.get(index_t);
            imageView.setTag(ad);

            ImageUtil.loadImage(ad.picUrl, imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


        @Override
        public void setPrimaryItem(android.view.ViewGroup container, int position, java.lang.Object object) {
            super.setPrimaryItem(container, position, object);
            if (object instanceof View) {
                if (mList.size() == 0) {
                    return;
                }
                int index_t = position % mList.size();
                CarouselData ad = mList.get(index_t);
                ImageView imageView = (ImageView) object;
                ImageUtil.loadImage(ad.picUrl, imageView);
            }
        }
    }


}

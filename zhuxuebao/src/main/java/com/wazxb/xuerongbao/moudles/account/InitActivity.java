package com.wazxb.xuerongbao.moudles.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.databinding.ActivityInitBinding;

public class InitActivity extends ZXBBaseActivity {
    private int[] IMGS = new int[]{R.drawable.start_1, R.drawable.start_3, R.drawable.start_2, R.drawable.start_4};
    ActivityInitBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_init);
        mBinding.bannerView.setAdapter(new BannerViewPagerAdapter());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class BannerViewPagerAdapter extends PagerAdapter {

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(InitActivity.this);
            container.addView(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(IMGS[position]);
            if (position == 3) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


        @Override
        public void setPrimaryItem(android.view.ViewGroup container, int position, java.lang.Object object) {
            super.setPrimaryItem(container, position, object);
        }
    }


}

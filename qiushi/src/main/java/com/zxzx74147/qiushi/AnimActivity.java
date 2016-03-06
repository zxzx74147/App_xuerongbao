package com.zxzx74147.qiushi;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zxzx74147.devlib.base.ZXBaseActivity;
import com.zxzx74147.devlib.utils.ZXViewHelper;

/**
 * Created by zhengxin on 16/3/5.
 */
public class AnimActivity extends ZXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        final RelativeLayout mRoot = (RelativeLayout) findViewById(R.id.root);

        for (int i = 0; i < 30; i++) {
            final int j = i;
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final ImageView imageView = new ImageView(AnimActivity.this);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.leftMargin = (j % 10) * 80;
                    lp.topMargin = (j / 10) * 200;
                    mRoot.addView(imageView, lp);
                    imageView.setImageResource(R.drawable.star);
                    ZXViewHelper.startFramAnim(imageView);
                    int time = ZXViewHelper.getAnimationTime((AnimationDrawable) imageView.getDrawable());
                    imageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageResource(R.drawable.star2);
                            ZXViewHelper.startFramAnim(imageView);
                        }
                    }, time);
                }
            }, i * 700);
        }

    }
}

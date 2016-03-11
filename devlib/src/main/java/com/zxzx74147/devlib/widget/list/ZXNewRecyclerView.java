package com.zxzx74147.devlib.widget.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.zxzx74147.devlib.utils.BdLog;

/**
 * Created by zhengxin on 16/3/11.
 */
public class ZXNewRecyclerView extends UltimateRecyclerView {

    public ZXNewRecyclerView(Context context) {
        super(context);
        init(null);
    }

    public ZXNewRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ZXNewRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BdLog.e("refresh");
            }
        });


    }
}

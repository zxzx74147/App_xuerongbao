package com.wazxb.zhuxuebao.moudles.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxzx74147.devlib.widget.BaseFragment;

/**
 * Created by zhengxin on 16/2/20.
 */
public class PersonalFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  new View(getContext());
        view.setBackgroundColor(Color.RED);
        return view;
    }
}

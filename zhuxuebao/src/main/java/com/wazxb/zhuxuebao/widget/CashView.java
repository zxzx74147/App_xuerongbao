package com.wazxb.zhuxuebao.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.CashViewBinding;
import com.wazxb.zhuxuebao.moudles.borrow.BorrowConfig;
import com.wazxb.zhuxuebao.storage.data.BorrowRequestData;
import com.wazxb.zhuxuebao.storage.data.ProdData;
import com.wazxb.zhuxuebao.util.CalculatorUtil;

import java.util.LinkedList;
import java.util.List;

import cn.jeesoft.widget.pickerview.CharacterPickerView;

/**
 * Created by zhengxin on 16/3/9.
 */
public class CashView extends LinearLayout {
    private CashViewBinding mBinding = null;
    private List<String> mAmounts = new LinkedList<>();
    private List<String> mTimes = new LinkedList<>();
    private int mAmount = 0;
    private int mTime = 0;
    private float mRate = 0;
    private ProdData mProdData;

    private int mMinMoney = 0;
    private int mMaxMoney = 0;
    private int mMinTime = 0;
    private int mMaxTime = 0;
    private int mMode = 0;
    private float value = 0;

    public CashView(Context context) {
        super(context);
        init(null);
    }

    public CashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.cash_view, this, true);

        setOrientation(VERTICAL);

        setData();
    }

    public void fillRequestData(BorrowRequestData data) {
        data.money = mAmount;
        data.day = mTime;
        data.month = mTime;
        data.returnNum = value;
        data.lnProdId = mProdData.lnProdId;
    }

    public void setData() {
        mAmounts.clear();
        mTimes.clear();
        for (int i = mMinMoney; i <= mMaxMoney; i += 50) {
            mAmounts.add(String.valueOf(i));
        }
        for (int i = mMinTime; i <= mMaxTime; i++) {
            mTimes.add(String.valueOf(i));
        }
        mBinding.picker1.setPicker(mAmounts);
        mBinding.picker2.setPicker(mTimes);
        mBinding.picker1.setOnOptionChangedListener(mAmountListener);
        mBinding.picker2.setOnOptionChangedListener(mTimeListener);
        calResult();
    }

    private CharacterPickerView.OnOptionChangedListener mAmountListener = new CharacterPickerView.OnOptionChangedListener() {
        @Override
        public void onOptionChanged(CharacterPickerView view, int option1, int option2, int option3) {
            mAmount = Integer.valueOf(mAmounts.get(option1));
            calResult();
        }
    };

    private CharacterPickerView.OnOptionChangedListener mTimeListener = new CharacterPickerView.OnOptionChangedListener() {
        @Override
        public void onOptionChanged(CharacterPickerView view, int option1, int option2, int option3) {
            mTime = Integer.valueOf(mTimes.get(option1));
            calResult();
        }
    };

    public void calResult() {
        switch (mMode) {
            case BorrowConfig.BORROW_YUELI:
                value = (float) CalculatorUtil.getMonth(mAmount, mRate, mTime);
                mBinding.dayPer.setText("/" + mTime + "月");
                break;
            case BorrowConfig.BORROW_FULI:
                value = mAmount;
                mBinding.dayPer.setText("/" + mTime + "天");
                break;
            case BorrowConfig.BORROW_HUOLI:
                value = (float) CalculatorUtil.getDay(mAmount, mRate, mTime);
                mBinding.dayPer.setText("/" + mTime + "天");
                break;
        }

        mBinding.value.setText(String.format("%.2f", value));

    }

    public void setProd(ProdData prod) {
        if (prod == null) {
            return;
        }
        mProdData = prod;
        mMinMoney = prod.minMoney;
        mMaxMoney = prod.maxMoney;
        mAmount = prod.minMoney;
        if (prod.lnProdId == BorrowConfig.BORROW_YUELI) {
            mMinTime = prod.minMonth;
            mMaxTime = prod.maxMonth;
            mTime = prod.minMonth;
        } else {
            mMinTime = prod.minDay;
            mMaxTime = prod.maxDay;
            mTime = prod.minDay;
        }
        mMode = prod.lnProdId;
        mRate = prod.rateFlt;
        setData();
    }
}

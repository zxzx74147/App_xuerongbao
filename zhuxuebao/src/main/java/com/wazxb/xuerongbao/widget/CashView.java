package com.wazxb.xuerongbao.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.wangjie.wheelview.WheelView;
import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.databinding.CashViewBinding;
import com.wazxb.xuerongbao.moudles.account.AccountManager;
import com.wazxb.xuerongbao.moudles.borrow.BorrowConfig;
import com.wazxb.xuerongbao.storage.data.BorrowRequestData;
import com.wazxb.xuerongbao.storage.data.ProdData;
import com.wazxb.xuerongbao.storage.data.UserAllData;
import com.wazxb.xuerongbao.util.CalculatorUtil;

import java.util.LinkedList;
import java.util.List;

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
        mBinding.picker1.setItems(mAmounts);
        mBinding.picker1.setOnWheelViewListener(mAmountListener);
        mBinding.picker2.setItems(mTimes);
        mBinding.picker2.setOnWheelViewListener(mTimeListener);
        mBinding.picker1.drawLine(false, true);
        mBinding.picker2.drawLine(true, false);
        calResult();
    }


    private WheelView.OnWheelViewListener mTimeListener = new WheelView.OnWheelViewListener() {
        public void onSelected(int selectedIndex, String item) {
            mTime = Integer.valueOf(mTimes.get(selectedIndex));
            calResult();
        }
    };

    private WheelView.OnWheelViewListener mAmountListener = new WheelView.OnWheelViewListener() {
        public void onSelected(int selectedIndex, String item) {
            mAmount = Integer.valueOf(mAmounts.get(selectedIndex));
            calResult();
        }
    };

    public void calResult() {
        switch (mMode) {
            case BorrowConfig.BORROW_YUELI:
                value = (float) CalculatorUtil.getMonth(mAmount, mRate, mTime);
                mBinding.dayPer.setText("/" + "月");
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

        mBinding.value.setText(String.format("%.02f元", value));

    }

    public void setProd(ProdData prod) {
        if (prod == null) {
            return;
        }
        UserAllData user = AccountManager.sharedInstance().getUserAllData();
        if (prod.lnProdId == BorrowConfig.BORROW_YUELI && user != null) {
            mMaxMoney = Math.max(user.user.quotaTotal, prod.maxMoney);
        } else {
            mMaxMoney = prod.maxMoney;
        }
        mProdData = prod;
        mMinMoney = prod.minMoney;
        mAmount = prod.minMoney;
        if (prod.lnProdId == BorrowConfig.BORROW_YUELI) {
            mMinTime = prod.minMonth;
            mMaxTime = prod.maxMonth;
            mTime = prod.minMonth;
            mBinding.time.setText(getResources().getText(R.string.borrow_time) + "（月）");
        } else {
            mMinTime = prod.minDay;
            mMaxTime = prod.maxDay;
            mTime = prod.minDay;
            mBinding.time.setText(getResources().getText(R.string.borrow_time) + "（天）");
        }
        mMode = prod.lnProdId;
        mRate = prod.rateFlt;
        setData();
    }
}

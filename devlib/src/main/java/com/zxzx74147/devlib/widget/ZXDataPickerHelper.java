package com.zxzx74147.devlib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.zxzx74147.devlib.R;
import com.zxzx74147.devlib.utils.TimerUtil;
import com.zxzx74147.devlib.utils.ZXDialogUtil;

/**
 * Created by zhengxin on 16/3/5.
 */
public class ZXDataPickerHelper {

    public static final int YEAR_CURRENT = TimerUtil.getCurrentYear();

    public interface IDateSelected {
        void onSelected(int year, int month, int day);
    }

    public static Dialog selectYear(Context context, final IDateSelected listener) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_date_picker, null);
        final NumberPicker mPicket = (NumberPicker) view.findViewById(R.id.date_picker);
        Button mCancel = (Button) view.findViewById(R.id.cancel);
        Button mOk = (Button) view.findViewById(R.id.ok);
        mPicket.setFormatter(mYearFormat);
        mPicket.setMaxValue(YEAR_CURRENT);
        mPicket.setMinValue(YEAR_CURRENT - 50);
        mPicket.setValue(YEAR_CURRENT - 3);
        final Dialog dialog = ZXDialogUtil.showDialog(context, view, null);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int year = mPicket.getValue();
                listener.onSelected(year, 0, 0);
            }
        });
        return dialog;

    }

    private static NumberPicker.Formatter mYearFormat = new NumberPicker.Formatter() {
        @Override
        public String format(int value) {
            return value + "年";
        }
    };
}
